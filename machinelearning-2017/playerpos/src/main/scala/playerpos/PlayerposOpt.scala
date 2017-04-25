package playerpos

import breeze.linalg.DenseMatrix.fill
import breeze.linalg.{DenseMatrix, DenseVector, _}
import breeze.numerics.sin
import breeze.optimize.{ApproximateGradientFunction, DiffFunction, LBFGS, Minimizer}
import common.Util

object PlayerposOpt {

  type DM = DenseMatrix[Double]
  type DV = DenseVector[Double]
  type HYP = (DM, DV) => DV

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


  def cost(x: DM, y: DV)(hypothesis: HYP)(theta: DV): Double = {
    val m = x.rows
    val h = hypothesis(x, theta)
    sum((h - y) ^:^ 2.0) / (2 * m)
  }

  def hypothesisSin(x: DM, theta: DV): DV = {
    require(theta.length == 4, "length of theta must be 4")
    val a = theta(0)
    val b = theta(1)
    val c = theta(2)
    val d = theta(3)
    val vd = DenseVector.fill(x.cols)(d)
    sin(x * vd + c) * b + a
  }

  def hypothesisSinExt(x: DM, theta: DV): DV = {
    val thetaLen = 4 * x.cols
    require(theta.length == thetaLen, s"length of theta must be $thetaLen")
    
    val thetas = theta.data.grouped(4).map { d => DenseVector(d) }
    
    // val vd = DenseVector.fill(x.cols)(d)
    // sin(x * vd + c) * b + a
    ???
  }

  def hypothesisPoli(grade: Int)(x: DM, theta: DV): DV = {
    val thetaLen = x.cols * grade
    require(theta.length == thetaLen, s"length of theta must be $thetaLen")
    
    val x1 = for(i <- 0 until x.cols; j <- 0 until grade) yield {
      x(::, i) ^:^ j.toDouble
    }  
    DenseMatrix(x1 : _*).t * theta
  }

  def hypothesisSin1(x: DM, theta: DV): DV = {
    require(theta.length == 3, "length of theta must be 3")
    val b = theta(0)
    val c = theta(1)
    val d = theta(2)
    val vd = DenseVector.fill(x.cols)(d)
    sin(x * vd + c) * b
  }

  def minimizerLbfgs(maxIter: Int): Minimizer[DV, DiffFunction[DV]] = {
    new LBFGS[DV](maxIter, 5)
  }

  def diffFunctionApprox(hyp: HYP)(x: DM, y: DV): DiffFunction[DV] = {
    val fcost = PlayerposOpt.cost(x, y)(hyp)(_)
    new ApproximateGradientFunction[Int, DV](fcost)
  }

  def diffFunctionApproxSin(x: DM, y: DV): DiffFunction[DV] = {
    val hyp = hypothesisSin _
    diffFunctionApprox(hyp)(x, y)
  }
  def diffFunctionApproxSin1(x: DM, y: DV): DiffFunction[DV] = {
    val hyp = hypothesisSin1 _
    diffFunctionApprox(hyp)(x, y)
  }

  def diffFunctionApproxPoli(grade: Int)(x: DM, y: DV): DiffFunction[DV] = {
    val hyp = hypothesisPoli(grade) _
    diffFunctionApprox(hyp)(x, y)
  }

  def readDataSet(yColIndex: Int)(fileName: String): (DM, DV) = {
    val file = Util.dataFile(fileName)
    val all = csvread(file, separator = ',')
    val x = all(::, 3 to 44)
    val y = all(::, yColIndex to yColIndex)
    val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
    (x1, y.toDenseVector)
  }

  def readDataSetX: String => (DM, DV) = readDataSet(0)(_)

  def readDataSetY: String => (DM, DV) = readDataSet(1)(_)

  def readDataSetDir: String => (DM, DV) = readDataSet(2)(_)

}

object TryoutLbfgs extends App {

  import PlayerposOpt._

  val ds = datasets(1)

  val (x, y) = readDataSetDir(ds.filename)
  val dfp = diffFunctionApproxPoli(4)(x, y)
  val dfs = diffFunctionApproxSin(x, y)

  val thetaInitial = DenseVector.zeros[Double](4)

  val iterations = List(1, 2, 3, 4, 5, 7, 10, 20, 100)

  iterations.foreach { iter =>
    val minimizer = minimizerLbfgs(iter)
    val thetaOpt = minimizer.minimize(dfs, thetaInitial)
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

  val fa = PlayerposOpt.diffFunctionApproxSin1(x, y)

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

object TryoutHypothesisPoli extends App {
  val x = DenseMatrix((1.0, 2.0), (4.0, 3.0))
  val theta = DenseVector(1.0, 1.0, 0.0, 2.0, 1.0, 1.0, 0.0, 2.0)
  
  val fh = PlayerposOpt.hypothesisPoli(4) _
  
  val h = fh(x, theta)
  require(h.length == x.rows)

  println("----- h poli -")
  println(h)

}

