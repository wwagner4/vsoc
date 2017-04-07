package machinelearning.verification

import breeze.linalg._
import breeze.optimize._

/**
  * Tryout for the breeze optimize algorithms
  */
object BreezeOptimize {

  val datasets = List(
    (10, "poly_10.txt"),
    (50, "poly_50.txt"),
    (100, "poly_100.txt"),
    (1000, "poly_1000.txt")
  )

  def cost(x: DenseMatrix[Double], y: DenseMatrix[Double])(theta: DenseVector[Double]): Double = {
    val y1 = y.t.toDenseVector
    val m = x.rows
    val h = x * theta
    sum((h - y1) ^:^ 2.0) / (2 * m)
  }


}

class LinRegDiffFunction(x: DenseMatrix[Double], y: DenseMatrix[Double]) extends DiffFunction[DenseVector[Double]] {

  val y1 = y.t.toDenseVector
  val m = x.rows

  def calculate(theta: DenseVector[Double]): (Double, DenseVector[Double]) = {
    val h = x * theta

    val c = sum((h - y1) ^:^ 2.0) / (2 * m)

    val d = (x.t * (h - y1)) *:* (1.0 / m)
    (c, d)
  }

}


object MainBreezeOptimize extends App {

  import BreezeOptimize._

  val grade = 3

  val (_, fname) = datasets(0)
  val (x, y) = VerificationUtil.readDataSet(fname)
  val x1 = VerificationUtil.polyExpand(x, grade)

  val f = new LinRegDiffFunction(x1, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)_)


  val t = DenseVector.zeros[Double](grade + 1)

  val maxIters = List(1, 2, 5, 10, 20, 100, 500)

  maxIters.foreach { mi =>
    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = mi, m = 3)
    val t1 = lbfgs.minimize(fa, t)
    println(mi + " - " + t1)
  }
}


object MainDiffFunction extends App {

  import BreezeOptimize._

  val grade = 3

  val (_, fname) = datasets(0)
  val (x, y) = VerificationUtil.readDataSet(fname)
  val x1 = VerificationUtil.polyExpand(x, grade)

  val f = new LinRegDiffFunction(x1, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)_)

  val th = DenseVector(0.001, 0.1, -0.02, 0.01)

  val t11 = f.calculate(th)
  val t12 = fa.calculate(th)

  println(t11)
  println(t12)


}


