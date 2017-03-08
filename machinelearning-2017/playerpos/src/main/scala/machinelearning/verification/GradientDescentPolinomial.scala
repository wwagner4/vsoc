package machinelearning.verification

import breeze.linalg.DenseMatrix.fill
import breeze.linalg.{DenseMatrix, DenseVector, Matrix, csvread}
import common.{Formatter, Util, Viz, VizCreatorGnuplot}
import machinelearning.TrainingSet

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

  def regerssion(): Unit = {

    def readDataSet(fileName: String): (DenseMatrix[Double], DenseMatrix[Double]) = {
      val file = Util.dataFile(fileName)
      val all = csvread(file, separator = ',')
      val x = all(::, 0 to 0)
      val y = all(::, 1 to 1)
      (x, y.toDenseMatrix)
    }

    def stepsTheta(x1: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Stream[DenseMatrix[Double]] = {
      import machinelearning.GradientDescent._
      import machinelearning.HypothesisFunction._

      val ts = TrainingSet(x1, y.toDenseMatrix)
      val thetIni = initialTheta(ts)
      val gd = gradientDescent(alpha)(linearFunc)(ts) _
      Stream.iterate(thetIni) { thet => gd(thet) }
    }



    val datasets = List(
      (10, "poly_10.txt"),
      (50, "poly_50.txt"),
      (100, "poly_100.txt"),
      (1000, "poly_1000.txt")
    )

    val (_, fileName) = datasets(0)
    val (x, y) = readDataSet(fileName)

    println(x)
    println(y)

  }

  def createData(): Unit = {

    val max = 100.0
    val min = -100.0
    val sizes = List(10, 50, 100, 1000)
    val theta = DenseVector(4400.0, -2000.0, -3, 0.7)
    val stdDev = 60000

    sizes.foreach { size =>
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

object MainPoliRegerssion extends App {

  GradientDescentPolinomial.regerssion()

}

object MainPoliCreateData extends App {

  GradientDescentPolinomial.createData()

}

object MainPolyTryout extends App {

  def polyExtend(grade: Int)(x: Double): DenseVector[Double] = {
    val v = DenseVector.range(0, grade)
    v.map(a => math.pow(x, a.toDouble))
  }

  val x = 2
  val t = DenseVector(1.2, 2.6, -4.1, -0.3)

  val x1 = polyExtend(t.length)(x)
  val y = x1.t * t

  println(x1)
  println(y)
}

