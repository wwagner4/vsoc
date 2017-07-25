import java.io.{File, IOException}

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.{Logger, LoggerFactory}

object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  new Training(log).train()
}

class Training(log: Logger) {

  import common.Util._

  val delim = ";"

  def train(): Unit = {
    log.info("start training")

    val dataFileNameTransformed = "random_pos_1000_xval.csv"

    log.info("Start read data")
    val dataSetIterator = readPlayerposXDataSet(new File(dataDir, dataFileNameTransformed), 15000)
    log.info("Start training")
    val nn = train(dataSetIterator)
    log.info("Finished training")

    val netFile = new File(dataDir, "nn.ser")
    ModelSerializer.writeModel(nn, netFile, true)
    log.info("Saved model to " + netFile)


  }


  def readPlayerposXDataSet(inFile: File, batchSize: Int): DataSetIterator = try {
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(inFile))
    new RecordReaderDataSetIterator(recordReader, batchSize, 42, 42, true)
  } catch {
    case e@(_: IOException | _: InterruptedException) =>
      throw new IllegalStateException("Error in 'readPlayerposXDataSet'. " + e.getMessage, e)
  }


  private def train(dataSetIterator: DataSetIterator): MultiLayerNetwork = {
    val nnConf = nnConfiguration
    //Create the network
    val net: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
    net.init()
    net.setListeners(new ScoreIterationListener(1))
    net.fit(dataSetIterator)
    net
  }


  /**
    * Returns the network configuration, 2 hidden DenseLayers
    */
  private def nnConfiguration = {
    val numHiddenNodes = 50
    val learningRate = 0.001
    val iterations = 3
    new NeuralNetConfiguration.Builder()
      .seed(234234L)
      .iterations(iterations)
      .optimizationAlgo(OptimizationAlgorithm.LINE_GRADIENT_DESCENT)
      .learningRate(learningRate)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .momentum(0.9)
      .list.layer(0, new DenseLayer.Builder()
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