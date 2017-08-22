package vsoc.training.genetic

import java.util

import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions

object GeneticTryout extends App {


  val nnConf = nnConfiguration()
  val nn = new MultiLayerNetwork(nnConf)
  nn.init()
  println("after init " + nn)

  for ((l, i) <- nn.getLayers.zipWithIndex) {
    println(s"l $i numParam: ${l.numParams()}")
    println(s"l $i W: ${util.Arrays.toString(l.getParam("W").shape())}")
    println(s"l $i b: ${util.Arrays.toString(l.getParam("b").shape())}")
  }




  private def nnConfiguration(): MultiLayerConfiguration = {
    val numHiddenNodes = 12
    new NeuralNetConfiguration.Builder()
      .seed(-1)
      .iterations(10)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(0.001)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .regularization(false)
      .momentum(0.9)
      .list
      .layer(0, new DenseLayer.Builder()
        .nIn(10)
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
