package machinelearning

import breeze.linalg._
import common.Util

object BreezeTryout extends App {

  difference()

  def difference(): Unit = {
    val m1 = DenseMatrix((1.0, 2.0, 3.0), (2.0, 3.0, 4.0))
    val m2 = DenseMatrix((4.0, 5.0, 6.0), (3.0, 4.0, 5.0))

    val diff = (m1 - m2) ^:^ 2.0
    val meanDiff = sum(diff) / diff.size

    println(diff)
    println(meanDiff)

    println("a:" + Util.meanDiff(m1, m2))
  }

  def concatMatrix(): Unit = {

    import DenseMatrix._

    val m1 = DenseMatrix((1.0, 2.0, 3.0), (2.0, 3.0, 4.0))
    val m2 = zeros[Double](m1.rows, 1)
    val m3 = horzcat(m2, m1)

    println(m1)
    println(m2)
    println(m3)
  }

}
