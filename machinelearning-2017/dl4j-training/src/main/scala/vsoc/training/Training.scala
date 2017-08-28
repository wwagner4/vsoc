package vsoc.training

import java.io.File

import org.apache.log4j.Level
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
import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Viz, VizCreator, VizCreatorGnuplot}
import vsoc.datavec.playerpos.CreateData
import vsoc.common.Dat
import vsoc.common.UtilIO.{dirSub, dirWork}
import vsoc.training.vizparam.Vizparam
import org.deeplearning4j.util.ModelSerializer
import vsoc.training.util.UtilViz


case class Regularisation(l1: Double, l2: Double, l1Bias: Double, l2Bias: Double)

case class MetaParamRun(
                         description: Option[String] = None,
                         descriptionMd: Option[String] = None,
                         clazz: String,
                         imgWidth: Int,
                         imgHeight: Int,
                         columns: Int,
                         series: Seq[MetaParamSeries]
                       )


case class MetaParamSeries(
                            description: String,
                            descriptionX: String,
                            descriptionY: String = "error",
                            yRange: (Double, Double) = (-60.0, 60.0),
                            metaParams: Seq[MetaParam]
                          )

case class MetaParam(
                      id: Option[String] = None,
                      description: String,
                      learningRate: Double = 0.0001,
                      trainingData: Dat.DataDesc = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, Dat.Size_500000),
                      batchSizeTrainingDataRelative: Double = 0.1,
                      testData: Dat.DataDesc = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000),
                      iterations: Int = 200,
                      optAlgo: OptimizationAlgorithm = OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT,
                      regularisation: Option[Regularisation] = None,
                      numHiddenNodes: Int = 100,
                      seed: Long = 1L,
                      variableParmDescription: () => String
                    )


object Training {

  def apply(): Training = {
    val _dirOut = dirOut
    initLogging(_dirOut)
    val log: Logger = LoggerFactory.getLogger(classOf[Training])
    new Training(log, _dirOut)
  }

  def initLogging(dir: File): Unit = {
    import org.apache.log4j.FileAppender
    import org.apache.log4j.Logger
    import org.apache.log4j.PatternLayout

    val fa = new FileAppender
    fa.setName("FileLogger")
    fa.setFile(new File(dir, "training.log").getAbsolutePath)
    fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"))
    fa.setThreshold(Level.DEBUG)
    fa.setAppend(true)
    fa.activateOptions()

    Logger.getRootLogger.addAppender(fa)
  }

  def dirOut: File = {
    val work = dirSub(dirWork, "playerpos_x")
    val ts = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new java.util.Date())
    dirSub(work, ts)
  }

}

class Training(log: Logger, _dirOut: File) {

  type L = Viz.X

  val delim = ";"

  def run(run: MetaParamRun): Unit = {

    Vizparam.fileHtml(run, _dirOut, "params.html")
    if (run.descriptionMd.isDefined) {
      descriptionHtml(run.descriptionMd.get, _dirOut)
    }
    val dias = run.series.map { s => trainSerie(s) }

    val dia = Viz.MultiDiagram[L](id = "playerpos_x",
      imgWidth = run.imgWidth,
      imgHeight = run.imgHeight,
      title = run.description,
      columns = run.columns,
      diagrams = dias)


    implicit val vicCreator: VizCreator[L] = VizCreatorGnuplot[L](_dirOut, _dirOut, execute = true)

    Viz.createDiagram(dia)
    log.info(s"output in ${_dirOut}")
  }

  def descriptionHtml(descMd: String, dirOut: File): Unit = ???

  def trainSerie(serie: MetaParamSeries): Viz.Diagram[L] = {
    val drs: Seq[Viz.DataRow[L]] = serie.metaParams.map(mparam => train(mparam))
    val (yFrom, yTo) = serie.yRange
    Viz.Diagram(id = "_",
      title = serie.description,
      yLabel = Some(serie.descriptionY),
      xLabel = Some(serie.descriptionX),
      yRange = Some(Viz.Range(Some(yFrom), Some(yTo))),
      xZeroAxis = true,
      dataRows = drs)
  }

  def train(mparam: MetaParam): Viz.DataRow[L] = {
    require(mparam.testData != mparam.trainingData, "Test- and training data must be different")
    val trainingData = readPlayerposXDataSet(mparam.trainingData, mparam.batchSizeTrainingDataRelative)
    val nnConf: MultiLayerConfiguration = nnConfiguration(mparam)
    if (log.isDebugEnabled()) {
      log.debug("Start training - " + mparam.description)
      log.debug("Start training - nnconf: \n" + nnConf.toYaml)
    } else {
      log.info("Start training - " + mparam.description)
    }
    val nn = train(trainingData, nnConf)
    mparam.id.foreach { id => saveNn(nn, id) }
    test(nn, mparam)
  }

  def saveNn(nn: MultiLayerNetwork, id: String): Unit = {
    val file = new File(_dirOut, s"nn_$id.ser")
    ModelSerializer.writeModel(nn, file, true)
    log.info(s"saved nn to $file")
  }

  private def train(data: DataSetIterator, nnConf: MultiLayerConfiguration): MultiLayerNetwork = {
    val nn: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
    nn.init()
    nn.setListeners(new ScoreIterationListener(50))
    nn.fit(data)
    nn
  }

  def test(nn: MultiLayerNetwork, metaParam: MetaParam): Viz.DataRow[L] = {

    val testDataSet: DataSet = readPlayerposXDataSet(metaParam.testData, 1.0).next()

    val features: INDArray = testDataSet.getFeatures
    val labels: INDArray = testDataSet.getLabels

    val out: INDArray = nn.output(features)

    val diff: INDArray = labels.sub(out)
    val all: INDArray = Nd4j.hstack(labels, diff)

    val _data: Seq[L] = UtilViz.convertX(all, 1)

    Viz.DataRow(
      style = Viz.Style_BOXPLOT,
      name = Some(metaParam.variableParmDescription()),
      data = _data)
  }

  def readPlayerposXDataSet(desc: Dat.DataDesc, batchSizeRelative: Double): DataSetIterator = {
    val infile = CreateData.createDataFile(desc)
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(infile))
    new RecordReaderDataSetIterator(recordReader, (desc.size.size * batchSizeRelative).toInt, 42, 42, true)
  }

  /**
    * Returns the network configuration, 2 hidden DenseLayers
    */
  private def nnConfiguration(mparam: MetaParam): MultiLayerConfiguration = {

    val nullReg = Regularisation(0.0, 0.0, 0.0, 0.0)

    new NeuralNetConfiguration.Builder()
      .seed(mparam.seed)
      .iterations(mparam.iterations)
      .optimizationAlgo(mparam.optAlgo)
      .learningRate(mparam.learningRate)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .regularization(mparam.regularisation.isDefined)
      .l1(mparam.regularisation.getOrElse(nullReg).l1)
      .l2(mparam.regularisation.getOrElse(nullReg).l2)
      .l1Bias(mparam.regularisation.getOrElse(nullReg).l1Bias)
      .l2Bias(mparam.regularisation.getOrElse(nullReg).l2Bias)
      .momentum(0.9)
      .list
      .layer(0, new DenseLayer.Builder()
        .nIn(42)
        .nOut(mparam.numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(1, new DenseLayer.Builder()
        .nIn(mparam.numHiddenNodes)
        .nOut(mparam.numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
        .activation(Activation.IDENTITY).nIn(mparam.numHiddenNodes)
        .nOut(1)
        .build)
      .pretrain(false)
      .backprop(true)
      .build
  }


}