package vsoc.training.util

import java.util

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.cpu.nativecpu.NDArray
import org.nd4j.linalg.factory.Nd4j

case class NetworkShape(layers: Seq[NetworkLayerShape])

case class NetworkLayerShape(paramsW: NetworkParamShape, pramsB : NetworkParamShape)

case class NetworkParamShape(params: Seq[Int])

sealed trait Param {
  def code: String
}

case object Param_W extends Param {
  val code = "W"
}
case object Param_b extends Param {
  val code = "b"
}



object UtilNetwork {

  def shape(nn: MultiLayerNetwork): NetworkShape = ???

  def printParam(nn: MultiLayerNetwork) = {
    for ((l, i) <- nn.getLayers.zipWithIndex) {
      println(s"--- layer $i ---")
      println(s"l $i W: ${util.Arrays.toString(l.getParam("W").shape())}")
      println(s"l $i W: ${l.getParam("W")}")
      println(s"l $i b: ${util.Arrays.toString(l.getParam("b").shape())}")
      println(s"l $i b: ${l.getParam("b")}")
    }
  }

  def shape(nd: INDArray): Seq[Int] = nd.shape().toList

  def setNetworkParam(value: Double, nn: MultiLayerNetwork, layerIndex: Int, _param: Param): Unit = {
    require(nn.getnLayers() > layerIndex, s"Layer index $layerIndex must be smaller than number of layers ${nn.getnLayers()}")
    val layer = nn.getLayer(layerIndex)
    val paramShape = shape(layer.getParam(_param.code))
    require(paramShape == Seq(1, 1), "Shape of parameter must be [1, 1]")
    layer.setParam(_param.code, Nd4j.create(Array(value)))
    nn.init()
  }

  def setNetworkParam(value: Array[Double], nn: MultiLayerNetwork, layerIndex: Int, _param: Param): Unit = {
    require(value.length > 0, "Size of data muts be greater 0")
    require(nn.getnLayers() > layerIndex, "Layer index must be smaller than number of layers")
    val layer = nn.getLayer(layerIndex)
    val paramShape = shape(layer.getParam(_param.code))
    require(paramShape == Seq(1, value.length), s"Shape of parameter must be [1, ${value.length}]")
    layer.setParam(_param.code, Nd4j.create(value))
    nn.init()
  }

  def setNetworkParamTransposed(value: Array[Double], nn: MultiLayerNetwork, layerIndex: Int, _param: Param): Unit = {
    require(value.length > 0, "Size of data muts be greater 0")
    require(nn.getnLayers() > layerIndex, "Layer index must be smaller than number of layers")
    val layer = nn.getLayer(layerIndex)
    val paramShape = shape(layer.getParam(_param.code))
    require(paramShape == Seq(value.length, 1), s"Shape of parameter must be [${value.length}, 1]")
    layer.setParam(_param.code, Nd4j.create(value))
    nn.init()
  }

  def setNetworkParam(value: Array[Array[Double]], nn: MultiLayerNetwork, layerIndex: Int, _param: Param): Unit = {
    require(allEqual(value.map(_.length)), "All arrays must have the same size")
    require(value.length > 0, "Size of data muts be greater 0")
    require(value(0).length > 0, "Size of data muts be greater 0")
    require(nn.getnLayers() > layerIndex, "Layer index must be smaller than number of layers")
    val layer = nn.getLayer(layerIndex)
    val paramShape = shape(layer.getParam(_param.code))
    require(paramShape == Seq(1, value.length), s"Shape of parameter must be [${value(0).length}, ${value.length}]")
    layer.setParam(_param.code, Nd4j.create(value))
    nn.init()
  }

  def allEqual[U](seq: Iterable[U]): Boolean = {
    if (seq.isEmpty) true
    else seq.tail.foldLeft(true)((a: Boolean, b: U) => a && b == seq.head)
  }

}
