package machinelearning.verification

import breeze.linalg._
import common.Util

/**
  * Created by wwagner4 on 13/03/2017.
  */
object VeriUtil {

  def readDataSet(fileName: String): (DenseMatrix[Double], DenseMatrix[Double]) = {
    val file = Util.dataFile(fileName)
    val all = csvread(file, separator = ',')
    val x = all(::, 0 to 0)
    val y = all(::, 1 to 1)
    (x, y.toDenseMatrix)
  }

  def polyExpand(x: DenseMatrix[Double], grade: Int): DenseMatrix[Double] = {
    val g1 = grade + 1
    val cols = g1 * x.cols
    val rows = x.rows
    val xArray = x.t.toArray.flatMap(v => Seq.fill(g1)(v))
    val x1 = DenseMatrix.create(cols, rows, xArray).t

    val len = rows * cols
    val expArray = (0 until len).map(_ % g1).map(_.toDouble).toArray
    val exp = DenseMatrix.create(cols, rows, expArray).t

    x1 :^= exp
  }

  def poly(x: Double)(theta: DenseVector[Double]): Double = {
    val exp = DenseVector.range(0, theta.length)
    exp.map(e => math.pow(x, e.toDouble)).t * theta
  }


}
