package machinelearning.verification

import breeze.linalg.DenseVector
import common.{Formatter, Util, Viz, VizCreatorGnuplot}

/**
  * Polynomial function that should be easily learned
  * with linear gradient descent.
  * Purpose: Test of learning algorithms
  */
object GradientDescentPolinomial {

  val random = new java.util.Random()

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  def poly(x: Double)(theta: DenseVector[Double]): Double = {
    val exp = DenseVector.range(0, theta.length)
    exp.map(e => math.pow(x, e.toDouble)).t * theta
  }

  def polyRandomized(x: Double, deviation: Double)(theta: DenseVector[Double]): Double = {
    val r = random.nextDouble() * 2.0 * deviation - deviation
    return poly(x)(theta) + r
  }




  def plotPoly(): Unit = {
    val xs = -100.0 to(100.0, 1)

    val theta = DenseVector(4400.0, -2000.0, -3, 0.7)

    val ys = xs.map { x => (x, GradientDescentPolinomial.polyRandomized(x, 60000)(theta)) }

    val data = ys.map {
      case (x, y) => Viz.XY(x, y)
    }

    val thetaStr = Formatter.format(theta.toArray)

    val dr = Viz.DataRow(thetaStr, style = Viz.Style_POINTS, data = data)

    val drs = List(dr)

    val dia = Viz.Diagram("poly", "Polynomial Function vectorized", dataRows = drs)

    Viz.createDiagram(dia)

  }


}

object MainPoli extends App {

  GradientDescentPolinomial.plotPoly()

}

object MainTryout extends App {

  val x = 2.3
  val t = DenseVector(1.2, -2.6, 4.1)

  val v = DenseVector.range(0, t.length)
  val y = v.map(a => math.pow(x, a.toDouble)).t * t


  println(y)

}