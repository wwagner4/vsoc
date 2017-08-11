package vsoc.training

import java.io.File

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.Logger
import vsoc.common.{Viz, VizCreatorGnuplot}

case class MetaParamRun(
                         description: Option[String] = None,
                         imgWidth: Int,
                         imgHeight: Int,
                         columns: Int,
                         series: Seq[MetaParamSeries]
                       )


case class MetaParamSeries(
                            description: String,
                            metaParams: Seq[MetaParam]
                          )

case class MetaParam(
                      description: String,
                      learningRate: Double = 0.001,
                      sizeTrainingData: Int = 1000000,
                      batchSizeTrainingDataRelative: Double = 0.8,
                      sizeTestData: Int = 1000,
                      batchSizeTestData: Int = 1000,
                      iterations: Int = 3,
                      seed: Long = 1L,
                      variableParmDescription: () => String
                    )


class Training(log: Logger) {

  type L = Viz.X

  import vsoc.common.UtilIO._

  val delim = ";"

  def trainSeries(run: MetaParamRun): Unit = {

    val _dirOut = dirOut

    val dias = run.series.map { s => trainSerie(s) }

    val dia = Viz.MultiDiagram[L](id = "playerpos_x",
      imgWidth = run.imgWidth,
      imgHeight = run.imgHeight,
      title = run.description,
      columns = run.columns,
      diagrams = dias)


    implicit val vicCreator = VizCreatorGnuplot[L](dirScripts, _dirOut, execute = true)

    Viz.createDiagram(dia)
    log.info(s"output in ${_dirOut}")
  }

  def trainSerie(serie: MetaParamSeries): Viz.Diagram[L] = {
    val drs: Seq[Viz.DataRow[L]] = serie.metaParams.map(mparam => train(mparam))
    Viz.Diagram(id = "_",
      title = serie.description,
      yLabel = Some("error"),
      xLabel = Some(serie.metaParams(0).description),
      yRange = Some(Viz.Range(Some(-60), Some(60))),
      dataRows = drs)

  }

  def dirOut: File = {
    val work = dirSub(dirWork, "playerpos_x")
    val ts = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new java.util.Date())
    dirSub(work, ts)
  }

  def train(mparam: MetaParam): Viz.DataRow[L] = {
    require(mparam.sizeTestData != mparam.sizeTrainingData, "Test- and training data must be different")
    val trainingDataFileName = s"random_pos_${mparam.sizeTrainingData}_xval.csv"
    val trainingDataFile = new File(dirData, trainingDataFileName)
    val trainingData = readPlayerposXDataSet(trainingDataFile, mparam.sizeTrainingData, mparam.batchSizeTrainingDataRelative)
    val nnConf: MultiLayerConfiguration = nnConfiguration(mparam)
    log.info("Start training")
    val nn = train(trainingData, nnConf)
    test(nn, mparam)
  }

  private def train(data: DataSetIterator, nnConf: MultiLayerConfiguration): MultiLayerNetwork = {
    val nn = new MultiLayerNetwork(nnConf)
    nn.init()
    nn.setListeners(new ScoreIterationListener(50))
    nn.fit(data)
    nn
  }

  def test(nn: MultiLayerNetwork, metaParam: MetaParam): Viz.DataRow[L] = {

    val testDataFileName = s"random_pos_${metaParam.sizeTestData}_xval.csv"
    val testDataFile = new File(dirData, testDataFileName)
    val testDataSet: DataSet = readPlayerposXDataSet(testDataFile, metaParam.sizeTestData, metaParam.sizeTestData).next()

    val features: INDArray = testDataSet.getFeatures
    val labels: INDArray = testDataSet.getLabels

    val out: INDArray = nn.output(features)

    val diff: INDArray = labels.sub(out)
    val all: INDArray = Nd4j.hstack(labels, diff)

    val _data: Seq[L] = UtilTraining.convertX(all, 1)

    Viz.DataRow(
      style = Viz.Style_BOXPLOT,
      name = Some(metaParam.variableParmDescription()),
      data = _data)
  }

  def readPlayerposXDataSet(inFile: File, size: Int, batchSizeRelative: Double): DataSetIterator = {
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(inFile))
    new RecordReaderDataSetIterator(recordReader, (size * batchSizeRelative).toInt, 42, 42, true)
  }

  /**
    * Returns the network configuration, 2 hidden DenseLayers
    */
  private def nnConfiguration(mparam: MetaParam): MultiLayerConfiguration = {
    val numHiddenNodes = 50
    new NeuralNetConfiguration.Builder()
      .seed(mparam.seed)
      .iterations(mparam.iterations)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(mparam.learningRate)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .momentum(0.9)
      .list
      .layer(0, new DenseLayer.Builder()
        .nIn(42)
        .nOut(numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(1, new DenseLayer.Builder()
        .nIn(numHiddenNodes)
        .nOut(numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
        .activation(Activation.IDENTITY).nIn(numHiddenNodes)
        .nOut(1)
        .build)
      .pretrain(false)
      .backprop(true)
      .build
  }


}