import java.io.{File, IOException}

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
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.{Logger, LoggerFactory}

object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  new Training(log).train()
}

case class MetaParam(
                           learningRate: Double = 0.001,
                           sizeTrainingData: Int = 1000,
                           batchSizeTrainingData: Int = 100,
                           sizeTestData: Int = 50000,
                           batchSizeTestData: Int = 5000
                         )

class Training(log: Logger) {

  import common.Util._

  val delim = ";"

  def train(): Unit = {
    val metas = Seq(0.01, 0.001, 0.0001).map(lr => MetaParam(learningRate = lr))
    log.info("start training")
    metas.map(mparam => train(mparam))
  }

  def test(nn: MultiLayerNetwork, testDataFileName: String, sizeTestData: Int): Double = {

    val dataFileNameTransformed = s"random_pos_${sizeTestData}_xval.csv"
    val dataSet = readPlayerposXDataSet(new File(dataDir, dataFileNameTransformed), sizeTestData).next()

    val features = dataSet.getFeatures
    val labels = dataSet.getLabels

    val output = nn.feedForward(features, false)

    val out = output.get(output.size - 1)
    val diff = labels.sub(out)

    log.info("" + diff)

    0.0
  }

  def train(mparam: MetaParam): Double = {

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