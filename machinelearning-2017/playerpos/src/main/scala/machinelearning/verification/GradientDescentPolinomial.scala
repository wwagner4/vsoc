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
    val ran = random.nextDouble() * 2.0 * deviation - deviation
    poly(x)(theta) + ran
  }


  def createData(): Unit = {

    val max = 100.0
    val min = -100.0
    val sizes = List(10, 50, 100, 1000)
    val theta = DenseVector(4400.0, -2000.0, -3, 0.7)
    val stdDev = 60000

    sizes.foreach {size =>
      val steps = (max - min) / size
      val id = s"poly_$size"
      val file = Util.dataFile(s"$id.txt")
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, GradientDescentPolinomial.polyRandomized(x, stdDev)(theta)) }
      Util.writeToFile(file, { pw =>
        val data = ys.map {
          case (x, y) =>
            pw.println(Formatter.format(x, y))
            Viz.XY(x, y)
        }
        val thetaStr = Formatter.format(theta.toArray)
        val dr = Viz.DataRow(thetaStr, style = Viz.Style_POINTS, data = data)
        val dia = Viz.Diagram(id, s"Polynom datasize=$size", dataRows = List(dr))
        Viz.createDiagram(dia)
      })
      println(s"wrote data to $file")
    }
  }
}

object MainPoliCreateData extends App {

  GradientDescentPolinomial.createData()

}

object MainPolyTryout extends App {

  val x = 2.3
  val t = DenseVector(1.2, -2.6, 4.1)

  val v = DenseVector.range(0, t.length)
  val y = v.map(a => math.pow(x, a.toDouble)).t * t


  println(y)

}

