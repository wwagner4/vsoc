package playerpos

import breeze.linalg.DenseMatrix._
import breeze.linalg._
import breeze.numerics._
import breeze.optimize._
import common.Util
import playerpos.PlayerposOpt.DV

object PlayerposOpt {

  type DM = DenseMatrix[Double]
  type DV = DenseVector[Double]
  type HYP = (DM, DV) => DV

  case class Dataset(
                      filename: String,
                      size: Int
                    )

  val datasets = List(
    Dataset("pos_20.txt", 20),
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
    val paramCnt = 4
    val thetaLen = paramCnt * x.cols
    require(theta.length == thetaLen, s"length of theta must be $thetaLen")

    val thetas = theta.data.grouped(paramCnt).toSeq

    val vecs = for (i <- 0 until x.cols) yield {
      val vx = x(::, i)
      val theta = thetas(i)
      val a = theta(0)
      val b = theta(1)
      val c = theta(2)
      val d = theta(3)
      val v1 =  vx * d
      //println(s"H v1:$v1")
      val re = sin(v1 + c) * b + a
      re
    }
    val x1 = DenseMatrix(vecs: _*).t
    //println(s"H x1:\n$x1")
    sum(x1(*, ::))
  }

  def hypothesisPoli(grade: Int)(x: DM, theta: DV): DV = {
    val thetaLen = x.cols * grade
    require(theta.length == thetaLen, s"length of theta must be $thetaLen")

    val x1 = for (i <- 0 until x.cols; j <- 0 until grade) yield {
      x(::, i) ^:^ j.toDouble
    }
    DenseMatrix(x1: _*).t * theta
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

  def minimizerLbfgsb(size: Int, min: Double = -100.0, max: Double = 100.0)(maxIter: Int): Minimizer[DV, DiffFunction[DV]] = {
    new LBFGSB(
      DenseVector.fill(size)(-10.0),
      DenseVector.fill(size)(10.0),
      maxIter)
  }

  def diffFunctionApprox(hyp: HYP)(x: DM, y: DV): DiffFunction[DV] = {
    val fcost = PlayerposOpt.cost(x, y)(hyp)(_)
    new ApproximateGradientFunction[Int, DV](fcost)
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

object TryoutMinimizer extends App {

  import PlayerposOpt._

  val iterations = List(1, 2, 10, 100)
  
  iterations.foreach { iter =>
    val ds = datasets(1)
    //val (yName, (x, y)) = ("X", readDataSetX(ds.filename))
    //val (yName, (x, y)) = ("Y", readDataSetY(ds.filename))
    val (yName, (x, y)) = ("dir", readDataSetDir(ds.filename))

    val (hypName, hyp) = ("SinExt", PlayerposOpt.hypothesisSinExt _)
    //val (hypName, hyp) = ("Sin", PlayerposOpt.hypothesisSin _)
    //val (hypName, hyp) = ("Poli", PlayerposOpt.hypothesisPoli(3) _)
    
    val diff = diffFunctionApprox(hyp)(x, y)
    val thetaInitial = DenseVector.zeros[Double](172)
    
    //val (minimizerName, minimizer = ("LBFGS", minimizerLbfgs(iter))
    val (minimizerName, minimizer) = ("LBFGSB", minimizerLbfgsb(thetaInitial.length)(iter))

    val thetaOpt = minimizer.minimize(diff, thetaInitial)

    println(f"iterations:$iter%d - datasize:${x.rows} - y(content):$yName - hypothesis:$hypName - minimizer:$minimizerName")
    println
    thetaOpt.data.grouped(16).foreach{grp =>
      println("           " + grp.map(format).mkString(","))
    }
    println
    println

  }
  
  def format(v: Double): String = f"$v%10.4f"

}

object TryoutApproxGradTryout extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  println("----- x -")
  println(x)
  val y = DenseVector(0.0, 1.0)
  println("----- y -")
  println(y)

  val hyp = PlayerposOpt.hypothesisSin1 _
  val diff = PlayerposOpt.diffFunctionApprox(hyp)(x, y)

  val thetas = List(
    DenseVector(1.0, 1.0, math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0),
    DenseVector(1.0, 1.0, 0.0, 2.0),
    DenseVector(1.0, 1.0, -math.Pi / 2.0, 2.0),
    DenseVector(1.0, 1.0, math.Pi, 2.0)
  )

  thetas.foreach(theta => println(diff(theta)))

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

  val theta = DenseVector(0.1)

  val h = PlayerposOpt.hypothesisSin _
  val cost = PlayerposOpt.cost(x, y)(h)(theta)

  println("----- cost -")
  println(cost)

}

object TryoutHypothesis extends App {

  val ran = new scala.util.Random()

  val x = DenseMatrix(
    (1.0, 0.0),
    (0.0, 1.0)
  )
  println("----- x -")
  println(x)

  val theta = DenseVector(0.0, 1.0, 0.0, math.Pi/2.0, 0.0, 0.0, 0.0, 0.0)

  val fh = PlayerposOpt.hypothesisSinExt _

  val h = fh(x, theta)
  require(h.length == x.rows)

  println("----- theta -")
  println(theta)
  println("----- h -")
  println(h)

  def ranVector(size: Int, from: Double, to: Double): DV = {
    def ranVal = ran.nextDouble() * (to - from) + from
    val a = (1 to size).map {_ => ranVal}.toArray
    DenseVector(a)
  }

}

