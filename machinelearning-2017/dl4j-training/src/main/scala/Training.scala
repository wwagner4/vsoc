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

class Training(log: Logger) {

  def train(): Unit = {
    log.info("start training")
  }

  private def train(dataSetIterator: DataSetIterator): Unit = {
    val nnConf = nnConfiguration
    //Create the network
    val net = new MultiLayerNetwork(nnConf)
    net.init()
    net.setListeners(new ScoreIterationListener(1))
    net.fit(dataSetIterator)
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