package machinelearning.verification

import breeze.linalg._
import breeze.optimize.DiffFunction

/**
  * Tryout for the breeze optimize algorithms
  */
object BreezeOptimize {


}

object MainBreezeOptimize extends App {

  val datasets = List(
    (10, "poly_10.txt"),
    (50, "poly_50.txt"),
    (100, "poly_100.txt"),
    (1000, "poly_1000.txt")
  )

  val grade = 3

  val (_, fname) = datasets(2)
  val (x, y) = VerificationUtil.readDataSet(fname)
  val x1 = VerificationUtil.polyExpand(x, grade)

  val f = new DiffFunction[DenseMatrix[Double]] {

    def calculate(theta: DenseMatrix[Double]) = {
      val m = x1.rows
      val c = sum((x1 * theta - y) ^:^ 2.0) / (2 * m)
      val d = (((x1 * theta) - y).t * x1).t
      (c, d)
    }
  }


  val t = DenseMatrix(5d, 0d, 0d, -3d)

  println("----t-")
  println(t)
  val (c, d) = f.calculate(t)
  println("----c-")
  println(c)
  println("----d-")
  println(d)

}
