package vsoc.ga.trainga.nn.impl

import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import vsoc.ga.trainga.nn.NeuralNet

abstract class NnWrapperAbstract extends NeuralNet {

  sealed trait ParamType {
    def code: String
  }

  case object B extends ParamType {
    def code = "b"
  }

  case object W extends ParamType {
    def code = "W"
  }

  case class ParamDesc(layerNum: Int, cols: Int, rows: Int, paramType: ParamType) {
    lazy val len: Int = cols * rows
  }

  val paramTypes = Seq(B, W)

  def numInputNodes: Int

  def numOutputNodes: Int

  lazy val nn: MultiLayerNetwork = {
    val nnConf = nnConfiguration()
    val re: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
    re.init()
    re
  }

  private lazy val paramDescs: List[ParamDesc] =
    (0 until nn.getnLayers()).toList.flatMap { i =>
      val layer = nn.getLayer(i)
      paramTypes.map { pt =>
        val p = layer.getParam(pt.code)
        ParamDesc(i, p.columns(), p.rows(), pt)
      }
    }.sortBy(_.paramType.code)

  private lazy val paramSize: Int = {
    paramDescs.map(_.len).sum
  }

  def output(in: Array[Double]): Array[Double] = {
    val in1: INDArray = Nd4j.create(in)
    val out: INDArray = nn.output(in1)
    out.data().asDouble()
  }


  def setParam(p: Array[Double]): Unit = {
    if (p.length != paramSize) throw new IllegalStateException(s"Illegal parameter size. is ${p.length} should be $paramSize")

    def set(p1: Array[Double], pds: List[ParamDesc]): Unit = {
      pds match {
        case Nil => ()
        case head :: tail =>
          val (h, t) = p1.splitAt(head.len)
          val nda = Nd4j.create(head.rows, head.cols)
          for (r <- 0 until head.rows; c <- 0 until head.cols) {
            val i = (r * head.cols) + c
            nda.put(r, c, h(i))
          }
          nn.getLayer(head.layerNum).setParam(head.paramType.code, nda)

          set(t, tail)
      }
    }

    set(p, paramDescs)
  }

  def getParam: Array[Double] = {
    paramDescs.toArray.flatMap { pd =>
      val l = nn.getLayer(pd.layerNum)
      val p = l.getParam(pd.paramType.code)
      val out = new Array[Double](pd.len)
      for (r <- 0 until pd.rows; c <- 0 until pd.cols) {
        val i = (r * pd.cols) + c
        val v = p.getDouble(r, c)
        out(i) = v
      }
      out
    }
  }

  protected def nnConfiguration(): MultiLayerConfiguration

}

