package common

import breeze.linalg._


/**
  * Some utility functions
  */
object BreezeUtil {

  def sliceRow(m: DenseMatrix[Double], row: Int): DenseMatrix[Double] = {
    val x1 = m(row, 0 until m.cols)
    DenseMatrix(x1.inner)
  }

  def meanDiff(m1: DenseMatrix[Double], m2: DenseMatrix[Double]): Double = {
    val diff = (m1 - m2) ^:^ 2.0
    sum(diff) / diff.size
  }

}
