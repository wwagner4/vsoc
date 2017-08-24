package vsoc.training.util

import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.scalatest.Matchers._
import org.scalatest._

class UtilNetworkSuite extends FunSuite {

  test("allEqual OK") {
    UtilNetwork.allEqual(Seq(1,1,1,1)) shouldBe true
    UtilNetwork.allEqual(Seq(1)) shouldBe true
    UtilNetwork.allEqual(Seq.empty[String]) shouldBe true
  }

  test("allEqual NOT OK") {
    UtilNetwork.allEqual(Seq(1,2,1,1)) shouldBe false
    UtilNetwork.allEqual(Seq(1, "")) shouldBe false
  }

  test("allEqual Array") {
    val array = Array(Array(1,2,3), Array(3,2,1))
    val sizes = array.map(a => a.length)
    UtilNetwork.allEqual(sizes) shouldBe true
  }

  test("allEqual Array not equal") {
    val array = Array(Array(1,2), Array(3,2,1))
    val sizes = array.map(a => a.length)
    UtilNetwork.allEqual(sizes) shouldBe false
  }

  test("set weights of output layer") {
    val nn = createNN
    nn.init()
    UtilNetwork.setNetworkParamTransposed(Array(11.11, 22.0, 33.0, 44.0, 55.0, 66.0), nn, 2, Param_W)
  }

  test("set bias of input layer") {
    val nn = createNN
    nn.init()
    UtilNetwork.setNetworkParam(Array(11.11, 22.0, 33.0, 44.0, 55.0, 66.0), nn, 0, Param_b)
  }

  test("set bias of output layer one value") {
    val nn = createNN
    nn.init()
    UtilNetwork.setNetworkParam(11.11, nn, 2, Param_b)
  }

  test("set bias of output layer one value in Array") {
    val nn = createNN
    nn.init()
    UtilNetwork.setNetworkParam(Array(11.11), nn, 2, Param_b)
  }

  test("set bias of output layer one value in Array of Array") {
    val nn = createNN
    nn.init()
    UtilNetwork.setNetworkParam(Array(Array(11.11)), nn, 2, Param_b)
  }

  test("set bias of output layer two values") {
    an [IllegalArgumentException] should be thrownBy {
      val nn = createNN
      nn.init()
      UtilNetwork.setNetworkParam(Array(11.11, 2.4), nn, 2, Param_b)
    }
  }

  def createNN = new MultiLayerNetwork(nnConf)

  def nnConf: MultiLayerConfiguration = {
    val numHiddenNodes = 6
    new NeuralNetConfiguration.Builder()
      .seed(-1)
      .iterations(1)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(0.001)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .regularization(false)
      .momentum(0.9)
      .list
      .layer(0, new DenseLayer.Builder()
        .nIn(5)
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
