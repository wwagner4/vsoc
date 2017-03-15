package machinelearning.verification

import breeze.linalg._
import breeze.optimize.{DiffFunction, LBFGS}

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

  val grade = 2

  val (_, fname) = datasets(0)
  val (x, y) = VerificationUtil.readDataSet(fname)
  val x1 = VerificationUtil.polyExpand(x, grade)
  val y1 = y.t.toDenseVector

  val f = new DiffFunction[DenseVector[Double]] {

    def calculate(theta: DenseVector[Double]): (Double, DenseVector[Double]) = {
      val m = x1.rows
      val h = x1 * theta
      val c = sum((h - y1) ^:^ 2.0) / (2 * m)

      val d = x1.t * (h - y1)
      (c, d)
    }
  }


  val t = DenseVector.zeros[Double](grade + 1)

  val maxIters = List(1, 2, 5, 10, 20, 100, 500)

  maxIters.foreach { mi =>
    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = mi, m = 7)
    val t1 = lbfgs.minimize(f, t)
    println(mi + " - " + t1)
  }


}
