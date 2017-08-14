package vsoc.training

import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions

object ParamTryout extends App {

  val mp = MetaParam(variableParmDescription = () => "")

  val cfg = nnConfiguration(mp)

  val json = cfg.toJson

  import scala.util.parsing.json._

  val jcfg: Option[Any] = JSON.parseFull(json)

  println(jcfg)

  jcfg match {
    case Some(map: Map[String, Any]) => printMap(0, map)
    case _ => println("no match")

  }

  def printMap(indent: Int, map: Map[String, Any]): Unit = {
    val s = map.map {
      case (k, v) => "%30s - %s" format(k, v.getClass)}.mkString("\n")
    println(s)
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
