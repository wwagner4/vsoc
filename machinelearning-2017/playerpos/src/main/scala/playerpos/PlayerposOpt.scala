package playerpos

import breeze.linalg._
import breeze.numerics.sin

object PlayerposOpt {

  case class Dataset(
                      filename: String,
                      size: Int
                    )

  List(
    Dataset("pos_1000.txt", 1000),
    Dataset("pos_5000.txt", 5000),
    Dataset("pos_10000.txt", 10000),
    Dataset("pos_50000.txt", 50000)
  )

  def cost(hypothesis: (DenseMatrix[Double], DenseVector[Double]) => DenseVector[Double], theta: DenseVector[Double])(x: DenseMatrix[Double], y: DenseVector[Double]): Double = {
    val m = x.rows
    val h = hypothesis(x, theta)
    println("-- h -")
    println(h)
    sum((h - y) ^:^ 2.0) / (2 * m)
  }

  def hypothesisSin(x: DenseMatrix[Double], theta: DenseVector[Double]): DenseVector[Double] = {
    require(theta.length == 4)
    val a = theta(0)
    val b = theta(1)
    val c = theta(2)
    val d = theta(3)
    val vd = DenseVector.fill(x.cols)(d)
    sin(x * vd + c) * b + a
  }

}

object TryoutHypothesisSin extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  println(x)

  val theta = DenseVector(1.0, 1.0, math.Pi / 2.0, 2.0)

  val hyp = PlayerposOpt.hypothesisSin(x, theta)

  println("----- hyp -")
  println(hyp)

}

object TryoutCostSin extends App {
  val x = DenseMatrix((0.0, 0.0, 0.0, 0.0, 0.0), (3.0, 3.0, 4.0, 5.0, 6.0))
  val y = DenseVector(1.0, 0.07)

  val theta = DenseVector(1.0, 1.0, 0.0, 2.0)

  val h = PlayerposOpt.hypothesisSin _
  val cost = PlayerposOpt.cost(h, theta)(x, y)

  println("----- cost -")
  println(cost)

}
