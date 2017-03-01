package machinelearning

import breeze.linalg.DenseMatrix

object BreezeTryout extends App {

  concatMatrix()

  def concatMatrix():Unit ={

    import DenseMatrix._

    val m1 = DenseMatrix((1.0, 2.0, 3.0), (2.0, 3.0, 4.0))
    val m2 = zeros[Double](m1.rows, 1)
    val m3 = horzcat(m2, m1)

    println(m1)
    println(m2)
    println(m3)
  }

}
