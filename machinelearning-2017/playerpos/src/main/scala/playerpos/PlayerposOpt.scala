package playerpos

import breeze.linalg.DenseMatrix.fill
import breeze.linalg.{DenseMatrix, DenseVector, _}
import breeze.numerics.sin
import breeze.optimize.{ApproximateGradientFunction, DiffFunction, LBFGS, Minimizer}
import common.Util

object PlayerposOpt {

  type DM = DenseMatrix[Double]
  type DV = DenseVector[Double]

  case class Dataset(
                      filename: String,
                      size: Int
                    )

  val datasets = List(
    Dataset("pos_1000.txt", 1000),
    Dataset("pos_5000.txt", 5000),
    Dataset("pos_10000.txt", 10000),
    Dataset("pos_50000.txt", 50000)
  )

  type Hypothesis = (DenseMatrix[Double], DenseVector[Double]) => DenseVector[Double]

  def cost(x: DenseMatrix[Double], y: DenseVector[Double])(hypothesis: Hypothesis)(theta: DenseVector[Double]): Double = {
    val m = x.rows
    val h = hypothesis(x, theta)
    sum((h - y) ^:^ 2.0) / (2 * m)
  }

  def hypothesisSin(x: DenseMatrix[Double], theta: DenseVector[Double]): DenseVector[Double] = {
    require(theta.length == 4, "length of theta must be 4")
    val a = theta(0)
    val b = theta(1)
    val c = theta(2)
    val d = theta(3)
    val vd = DenseVector.fill(x.cols)(d)
    sin(x * vd + c) * b + a
  }

  def minimizerLbfgs(maxIter: Int): Minimizer[DenseVector[Double], DiffFunction[DenseVector[Double]]] = {
    new LBFGS[DenseVector[Double]](maxIter, 5)
  }

  def diffFunctionApprox(x: DenseMatrix[Double], y: DenseVector[Double]): DiffFunction[DenseVector[Double]] = {
    val hypo = PlayerposOpt.hypothesisSin _
    val fcost = PlayerposOpt.cost(x, y)(hypo)(_)
    new ApproximateGradientFunction[Int, DenseVector[Double]](fcost)
  }

  def readDataSet(yColIndex: Int)(fileName: String): (DenseMatrix[Double], DenseVector[Double]) = {
    val file = Util.dataFile(fileName)
    val all = csvread(file, separator = ',')
    val x = all(::, 3 to 44)
    val y = all(::, yColIndex to yColIndex)
    val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
    (x1, y.toDenseVector)
  }

  def readDataSetX: String => (DenseMatrix[Double], DenseVector[Double]) = readDataSet(0)(_)

  def readDataSetY: String => (DenseMatrix[Double], DenseVector[Double]) = readDataSet(1)(_)

  def readDataSetDir: String => (DenseMatrix[Double], DenseVector[Double]) = readDataSet(2)(_)

}

object TryoutLbfgs extends App {

  import PlayerposOpt._

  val ds = datasets(0)

  val (x, y) = readDataSetDir(ds.filename)
  val df = diffFunctionApprox(x, y)

  val thetaInitial = DenseVector.zeros[Double](4)

  val iterations = List(1, 2, 3, 4, 5, 7, 10, 20, 100)

  iterations.foreach { iter =>
    val minimizer = minimizerLbfgs(iter)
    val thetaOpt = minimizer.minimize(df, thetaInitial)
    val thetaStr = thetaOpt.data.mkString(", ")
    println(f"$iter%10d $thetaStr")
  }

}

object TryoutApproxGradTryout extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  println("----- x -")
  println(x)
  val y = DenseVector(0.0, 1.0)
  println("----- y -")
  println(y)

  val fa = PlayerposOpt.diffFunctionApprox(x, y)

  val thetas = List(
    DenseVector(1.0, 1.0, math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0),
    DenseVector(1.0, 1.0, 0.0, 2.0),
    DenseVector(1.0, 1.0, -math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0)
  )

  thetas.foreach(theta => println(fa(theta)))

}

object TryoutReadData extends App {

  import PlayerposOpt._

  val Dataset(filename, _) = datasets(2)
  println(filename)
  val (x, y) = readDataSetDir(filename)
  println(s"x:(${x.rows}, ${x.cols})")
  println(s"y.len=${y.length}")
  val ystart = y.data.take(10).mkString(", ")
  println(s"y start [$ystart, ...]")
}

object TryoutHypothesisSin extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  println("----- x -")
  println(x)

  val thetas = List(
    DenseVector(1.0, 1.0, math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0),
    DenseVector(1.0, 1.0, 0.0, 2.0),
    DenseVector(1.0, 1.0, -math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0)
  )

  thetas.foreach { theta =>
    val hyp = PlayerposOpt.hypothesisSin(x, theta)
    println("----- hyp -")
    println(hyp)
  }

}

object TryoutCostSin extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  val y = DenseVector(1.0, 0.07)

  val theta = DenseVector(1.0, 1.0, 0.0, 2.0)

  val h = PlayerposOpt.hypothesisSin _
  val cost = PlayerposOpt.cost(x, y)(h)(theta)

  println("----- cost -")
  println(cost)

}
