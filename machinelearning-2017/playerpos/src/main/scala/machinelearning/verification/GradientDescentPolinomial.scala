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

  case class Params(
                     datasetIndex: Int,
                     grade: Int,
                     alpha: Double,
                     steps: Int,
                     description: String
                   )

  val paramList = List(
    Params(0, 2, 0.00000001, 400, "theta_1 still increasing"),
    Params(1, 2, 0.00000001, 400, "theta_1 still increasing"),
    Params(2, 2, 0.00000001, 400, "theta_1 still increasing"),
    Params(3, 2, 0.00000001, 400, "theta_1 still increasing"),

    Params(0, 3, 0.000000000001, 200, "theta_3 converging to 0.44"),
    Params(1, 3, 0.000000000001, 200, "theta_3 converging to 0.43"),
    Params(2, 3, 0.000000000001, 200, "theta_3 converging to 0.43"),
    Params(3, 3, 0.000000000001, 200, "theta_3 converging to 0.42"),

    Params(2, 2, 0.0000001, 4000, "instable alpha too great"),
    Params(2, 2, 0.00000005, 4000, "theta_1 increasing to 1126. theta_0 0.32"),
    Params(2, 2, 0.00000001, 4000, "theta_1 increasing to 291. theta_0 0.06"),
    Params(2, 2, 0.000000001, 4000, "theta_1 increasing to 30.94"),
    Params(2, 2, 0.0000000001, 4000, "theta_1 increasing to 3.11"),
    Params(2, 2, 0.00000000001, 4000, "theta_1 increasing to 0.31")
  )

  val datasets = List(
    (10, "poly_10.txt"),
    (50, "poly_50.txt"),
    (100, "poly_100.txt"),
    (1000, "poly_1000.txt")
  )

  val random = new java.util.Random()

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  def readDataSet(fileName: String): (DenseMatrix[Double], DenseMatrix[Double]) = {
    val file = Util.dataFile(fileName)
    val all = csvread(file, separator = ',')
    val x = all(::, 0 to 0)
    val y = all(::, 1 to 1)
    (x, y.toDenseMatrix)
  }

  def steps(x: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Stream[DenseMatrix[Double]] = {
    import machinelearning.GradientDescent._
    import machinelearning.HypothesisFunction._

    val ts = TrainingSet(x, y.toDenseMatrix)
    val thetIni = initialTheta(ts)
    val gd = gradientDescent(alpha)(linearFunc)(ts) _
    Stream.iterate(thetIni) { thet => gd(thet) }
  }

  def regerssionPrintTheta(): Unit = {

    def printThetas(params: Params): Unit = {
      val (_, fileName) = datasets(params.datasetIndex)
      val (x, y) = readDataSet(fileName)
      val x1 = polyExpand(x, params.grade)
      val thetaList = steps(x1, y, params.alpha).take(params.steps).toList

      val tl = thetaList.map(_.toArray).map(a => common.Formatter.formatLimitated(a))
      println(tl.mkString("\n"))
    }

    printThetas(paramList(0))

  }

  def createData(): Unit = {

    def poly(x: Double)(theta: DenseVector[Double]): Double = {
      val exp = DenseVector.range(0, theta.length)
      exp.map(e => math.pow(x, e.toDouble)).t * theta
    }

    def polyRandomized(x: Double, deviation: Double)(theta: DenseVector[Double]): Double = {
      val ran = random.nextDouble() * 2.0 * deviation - deviation
      poly(x)(theta) + ran
    }

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
      val ys = xs.map { x => (x, polyRandomized(x, stdDev)(theta)) }
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


  def polyExpand(x: DenseMatrix[Double], grade: Int): DenseMatrix[Double] = {
    val g1 = grade + 1
    val cols = g1 * x.cols
    val rows = x.rows
    val xArray = x.t.toArray.flatMap(v => Seq.fill(g1)(v))
    val x1 = DenseMatrix.create(cols, rows, xArray).t

    val len = rows * cols
    val expArray = (0 until len).map(_ % (g1)).map(_.toDouble).toArray
    val exp = DenseMatrix.create(cols, rows, expArray).t

    x1 :^= exp
  }

}

object MainPoliRegerssionPrintData extends App {

  GradientDescentPolinomial.regerssionPrintTheta()

}

object MainPoliCreateData extends App {

  GradientDescentPolinomial.createData()

}

object MainPolyTryout extends App {

  import breeze.linalg._
  import breeze.numerics._

  val x = DenseMatrix((1.0, 2.0, 3.0), (2.2, 2.3, 2.4)).t
  val grade = 3
  val x1 = GradientDescentPolinomial.polyExpand(x, grade)

  println(s"grade=$grade")
  println("------------x-\n" + x)
  println("------------x1-\n" + x1)
}

