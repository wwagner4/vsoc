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
import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Formatter, Viz, VizCreatorGnuplot}

import scala.util.Random


case class MetaParamSeries(
                          decription: String,
                          metaParams: Seq[MetaParam]
                          )

case class MetaParam(
                      learningRate: Double = 0.001,
                      sizeTrainingData: Int = 1000000,
                      batchSizeTrainingData: Int = 100000,
                      sizeTestData: Int = 1000,
                      batchSizeTestData: Int = 1000,
                      iterations: Int = 3,
                      seed: Long = 1L
                    )


object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  new Training(log).train()
}

class Training(log: Logger) {

  import vsoc.common.UtilIO._

  val delim = ";"

  def train(): Unit = {

    val metas = Seq(5, 20, 50, 100, 500, 1000).map { iter =>
      MetaParam(
        seed = Random.nextLong(),
        iterations = iter)
    }
    val dias: Seq[Viz.Diagram[Viz.XY]] = metas.map(mparam => train(mparam))

    val learningRateStr = Formatter.formatNumber("%.5f", metas(0).learningRate)

    val multiDia = Viz.MultiDiagram[Viz.XY](id = "playerpos_iter_D5",
      imgWidth = 1500,
      imgHeight = 2000,
      title = Some(s"Learning Rate $learningRateStr"),
      columns = 2,
      diagrams = dias)

    implicit val vicCreator = VizCreatorGnuplot[Viz.XY](dirData, dirOut, execute = true)

    Viz.createDiagram(multiDia)
  }

  def dirOut: File = {
    val work = dirSub(dirWork, "playerpos_x")
    val ts = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new java.util.Date())
    dirSub(work, ts);
  }

  def train(mparam: MetaParam): Viz.Diagram[Viz.XY] = {

    require(mparam.sizeTestData != mparam.sizeTrainingData, "Test- and training data must be different")
    val trainingDataFileName = s"random_pos_${mparam.sizeTrainingData}_xval.csv"
    val trainingDataFile = new File(dirData, trainingDataFileName)
    val trainingData = readPlayerposXDataSet(trainingDataFile, mparam.batchSizeTrainingData)
    val nnConf: MultiLayerConfiguration = nnConfiguration(mparam)
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

  def test(nn: MultiLayerNetwork, metaParam: MetaParam): Viz.Diagram[Viz.XY] = {
    import Formatter._

    val testDataFileName = s"random_pos_${metaParam.sizeTestData}_xval.csv"
    val testDataFile = new File(dirData, testDataFileName)
    val testDataSet: DataSet = readPlayerposXDataSet(testDataFile, metaParam.sizeTestData).next()

    val features: INDArray = testDataSet.getFeatures
    val labels: INDArray = testDataSet.getLabels

    val out: INDArray = nn.output(features)

    val diff: INDArray = labels.sub(out)
    val all: INDArray = Nd4j.hstack(labels, diff)

    val _data: Seq[Viz.XY] = UtilTraining.convertXY(all, (0, 1))
    val dr: Viz.DataRow[Viz.XY] = Viz.DataRow(style = Viz.Style_POINTS,
      data = _data)

    Viz.Diagram(id = "_",
      title = formatNumber("iterations: %d", metaParam.iterations),
      xLabel = Some("x"),
      yLabel = Some("diff"),
      xRange = Some(Viz.Range(Some(-60), Some(60))),
      yRange = Some(Viz.Range(Some(-60), Some(60))),
      dataRows = Seq(dr))
  }

  def readPlayerposXDataSet(inFile: File, batchSize: Int): DataSetIterator = {
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(inFile))
    new RecordReaderDataSetIterator(recordReader, batchSize, 42, 42, true)
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