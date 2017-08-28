package vsoc.training.util

import java.util

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import vsoc.common.{Dat, Viz}
import vsoc.datavec.playerpos.CreateData

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

  val delim = ";"

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

  def setNetworkParam(value: INDArray, nn: MultiLayerNetwork, layerIndex: Int, _param: Param): Unit = {
    require(value.length > 0, "Size of data muts be greater 0")
    require(nn.getnLayers() > layerIndex, "Layer index must be smaller than number of layers")
    val layer = nn.getLayer(layerIndex)
    val paramShape = shape(layer.getParam(_param.code))
    require(paramShape == shape(value), s"Shape of parameter must be $paramShape")
    layer.setParam(_param.code, value)
    nn.init()
  }

  def getNetworkParam(nn: MultiLayerNetwork, layerIndex: Int, _param: Param): INDArray = {
    require(nn.getnLayers() > layerIndex, "Layer index must be smaller than number of layers")
    val layer = nn.getLayer(layerIndex)
    layer.getParam(_param.code)
  }

  def allEqual[U](seq: Iterable[U]): Boolean = {
    if (seq.isEmpty) true
    else seq.tail.foldLeft(true)((a: Boolean, b: U) => a && b == seq.head)
  }

  def test(nn: MultiLayerNetwork, testData: Dat.DataDesc) = {
    val testDataSet: DataSet = readPlayerposXDataSet(testData, 1.0).next()

    val features: INDArray = testDataSet.getFeatures
    val labels: INDArray = testDataSet.getLabels

    val out: INDArray = nn.output(features)

    val diff: INDArray = labels.sub(out)
    val all: INDArray = Nd4j.hstack(labels, diff)

    val _data: Seq[Viz.X] = UtilViz.convertX(all, 1)
    _data
  }

  def readPlayerposXDataSet(desc: Dat.DataDesc, batchSizeRelative: Double): DataSetIterator = {
    val infile = CreateData.createDataFile(desc)
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(infile))
    new RecordReaderDataSetIterator(recordReader, (desc.size.size * batchSizeRelative).toInt, 42, 42, true)
  }



}
