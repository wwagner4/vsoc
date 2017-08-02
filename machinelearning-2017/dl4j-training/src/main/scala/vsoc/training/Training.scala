package vsoc.training

import java.io.{File, IOException}
import java.util

import common.{Viz, VizCreatorGnuplot}
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
import org.nd4j.linalg.api.buffer.DataBuffer
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.{Logger, LoggerFactory}
import common.Util._
import org.nd4j.linalg.dataset.DataSet


case class MetaParam(
                      learningRate: Double = 0.001,
                      sizeTrainingData: Int = 1000,
                      batchSizeTrainingData: Int = 100,
                      sizeTestData: Int = 50000,
                      batchSizeTestData: Int = 5000
                    )


object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val dia: Viz.Dia[Viz.XYZ] = new Training(log).train()
  Viz.createDiagram(dia)(VizCreatorGnuplot[Viz.XYZ](dataDir, execute=false))
}

class Training(log: Logger) {

  import common.Util._

  val delim = ";"

  def train(): Viz.Dia[Viz.XYZ] = {
    val metas = Seq(0.01, 0.001, 0.0001).map(lr => MetaParam(learningRate = lr))
    log.info("start training")
    val dias: Seq[Viz.Diagram[Viz.XYZ]] = metas.map(mparam => train(mparam))

    Viz.MultiDiagram[Viz.XYZ](id = "playerpos", columns= 3, diagrams = dias)
  }

  def test(nn: MultiLayerNetwork, testDataFileName: String, sizeTestData: Int): Viz.Diagram[Viz.XYZ] = {

    val dataFileName = s"random_pos_$sizeTestData.csv"
    val dataSet: DataSet = readPlayerposXDataSet(new File(dataDir, dataFileName), sizeTestData).next()

    val features: INDArray = dataSet.getFeatures
    val labels: INDArray = dataSet.getLabels

    println("features " + util.Arrays.toString(features.shape()))
    println("labels " + util.Arrays.toString(labels.shape()))

    val output: util.List[INDArray] = nn.feedForward(features, false)

    val out: INDArray = output.get(output.size - 1)
    val diff: INDArray = labels.sub(out)

    log.info("" + diff)

    val buf: DataBuffer = diff.data()


    val _data = Seq(
      Viz.XYZ(1,2,3),
      Viz.XYZ(2,3,4))

    Viz.DataRow

    val dr: Viz.DataRow[Viz.XYZ] = Viz.DataRow(name = Some("01"), data = _data)

    Viz.Diagram(id = "playerpos_x", title = "Playerpos X", dataRows = Seq(dr))
  }

  def train(mparam: MetaParam): Viz.Diagram[Viz.XYZ] = {

    require(mparam.sizeTestData != mparam.sizeTrainingData, "Test- and training data must be different")

    val trainingDataFileName = s"random_pos_${mparam.sizeTrainingData}_xval.csv"
    val testDataFileName = s"random_pos_${mparam.sizeTestData}_xval.csv"

    log.info("Start read data")
    val trainingData = readPlayerposXDataSet(new File(dataDir, trainingDataFileName), mparam.batchSizeTrainingData)
    val testData = readPlayerposXDataSet(new File(dataDir, testDataFileName), mparam.batchSizeTestData)
    log.info("Start training")
    val nn = train(trainingData, nnConfiguration(mparam))
    log.info("Finished training")
    test(nn, testDataFileName, mparam.sizeTestData)
  }


  /**
    * @return DataSetIterator from the data of a file with a certain batch size
    */
  def readPlayerposXDataSet(inFile: File, batchSize: Int): DataSetIterator = try {
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(inFile))
    new RecordReaderDataSetIterator(recordReader, batchSize, 42, 42, true)
  } catch {
    case e@(_: IOException | _: InterruptedException) =>
      throw new IllegalStateException("Error in 'readPlayerposXDataSet'. " + e.getMessage, e)
  }


  private def train(dataSetIterator: DataSetIterator, nnConf: MultiLayerConfiguration): MultiLayerNetwork = {
    // create the net
    val net: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
    net.init()
    net.setListeners(new ScoreIterationListener(1))
    // train the net
    net.fit(dataSetIterator)
    // return the trained net
    net
  }


  /**
    * Returns the network configuration, 2 hidden DenseLayers
    */
  private def nnConfiguration(mparam: MetaParam): MultiLayerConfiguration = {
    val numHiddenNodes = 50
    val learningRate = mparam.learningRate
    val iterations = 3
    new NeuralNetConfiguration.Builder()
      .seed(234234L)
      .iterations(iterations)
      .optimizationAlgo(OptimizationAlgorithm.LINE_GRADIENT_DESCENT)
      .learningRate(learningRate)
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