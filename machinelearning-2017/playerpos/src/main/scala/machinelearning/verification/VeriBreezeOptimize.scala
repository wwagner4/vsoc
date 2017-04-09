package machinelearning.verification

import breeze.linalg._
import breeze.optimize._

/**
  * Tryout for the breeze optimize algorithms
  */
object VeriBreezeOptimize {

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

  private val y1 = y.t.toDenseVector
  private val m = x.rows

  def calculate(theta: DenseVector[Double]): (Double, DenseVector[Double]) = {
    val h = x * theta
    val c = sum((h - y1) ^:^ 2.0) / (2 * m)
    val d = (x.t * (h - y1)) *:* (1.0 / m)
    (c, d)
  }

}


object VeriBreezeOptimizeMain extends App {

  import VeriBreezeOptimize._

  val grades = List(1, 2, 3)
  val datasetIndexes = List(0, 1, 2, 3)
  val maxIters = List(2, 5, 6, 7, 8, 9, 10, 20, 50, 60, 70, 80, 90, 100, 150)

  val thetas = for (grade <- grades; datasetIndex <- datasetIndexes; maxIter <- maxIters) yield {
    val (datasetSize, fname) = datasets(datasetIndex)
    val (x, y) = VeriUtil.readDataSet(fname)
    val x1 = VeriUtil.polyExpand(x, grade)

    val f = new LinRegDiffFunction(x1, y)
    val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

    val thetaInitial = DenseVector.zeros[Double](grade + 1)

    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = 3)
    (datasetSize, grade, maxIter, lbfgs.minimize(f, thetaInitial), lbfgs.minimize(fa, thetaInitial))

  }

  thetas.foreach { case (s, g, mi, t, ta) =>
    val tStr = common.Formatter.format(t.toArray)
    val taStr = common.Formatter.format(ta.toArray)
    println(f"$s%10d $g%10d $mi%10d | $tStr | $taStr")
  }
}


object CompareToApproximationMain extends App {

  import VeriBreezeOptimize._

  val grade = 3

  val (_, fname) = datasets(0)
  val (x, y) = VeriUtil.readDataSet(fname)
  val x1 = VeriUtil.polyExpand(x, grade)

  val f = new LinRegDiffFunction(x1, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

  val th = DenseVector(0.001, 0.1, -0.02, 0.01)

  val t11 = f.calculate(th)
  val t12 = fa.calculate(th)

  println(t11)
  println(t12)


}


