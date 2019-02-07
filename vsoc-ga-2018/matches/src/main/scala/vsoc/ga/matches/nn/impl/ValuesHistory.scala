package vsoc.ga.matches.nn.impl
import scala.collection.immutable

class ValuesHistory(val historyLength: Int, dataLength: Int) {

  require(historyLength > 1, s"History length must be greater 1 but is: $historyLength")
  require(dataLength > 1, s"Data length must be greater 1 but is: $dataLength")

  private var buffer = initBuffer
  private var index = 0

  def addData(data: Array[Double]): Unit = {
    require(data.length == dataLength, s"Length of data must be $dataLength but is: ${data.length}")
    index = (index + 1) % historyLength
    buffer(index) = data
  }

  def data: Array[Array[Double]] = {
    val _buf: immutable.Seq[Array[Double]] = for (i <- 0 until historyLength) yield {
      buffer((i + index) % historyLength)
    }
    _buf.toArray
  }

  private def initBuffer: Array[Array[Double]] = {
    val _buf: immutable.Seq[Array[Double]] = for (_ <- 0 until historyLength) yield {
      Array.fill(dataLength)(0.0)
    }
    _buf.toArray
  }

}
