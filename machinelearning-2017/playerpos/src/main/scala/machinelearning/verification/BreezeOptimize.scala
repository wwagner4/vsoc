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

  val (_, fname) = datasets(0)
  val (x, y) = VerificationUtil.readDataSet(fname)
  val x1 = VerificationUtil.polyExpand(x, grade)
  val y1 = y.t.toDenseVector

  val f = new DiffFunction[DenseVector[Double]] {

    def calculate(theta: DenseVector[Double]):(Double, DenseVector[Double]) = {
      val m = x1.rows
      val c1 = x1 * theta
      val c = sum((c1 - y1) ^:^ 2.0) / (2 * m)
      val d1 = c1 -y1
      val d = (x1.t * d1).toDenseVector
      (c, d)
    }
  }


  val t = DenseVector(5d, 0d, 0d, -3d)

  println("----t-")
  println(t)
  val (c, d) = f.calculate(t)
  println("----c-")
  println(c)
  println("----d-")
  println(d)

}
