package machinelearning.verification

import breeze.linalg.DenseMatrix.fill
import breeze.linalg.{DenseMatrix, DenseVector, Matrix, csvread}
import common._
import machinelearning.TrainingSet
import machinelearning.verification.GradientDescentPolinomial.Params


/**
  * Polynomial function that should be easily learned
  * with linear gradient descent.
  * Purpose: Test of learning algorithms
  */
object GradientDescentPolinomial {

  val max = 100.0
  val min = -100.0
  val sizes = List(10, 50, 100, 1000)
  val thetaOrig = DenseVector(4400.0, -2000.0, -3, 0.7)
  val stdDev = 60000

  case class Params(
                     id: String,
                     datasetIndex: Int,
                     grade: Int,
                     alpha: Double,
                     steps: Int,
                     description: String,
                     stepsOfInterest: List[Int]
                   )

  // Grade 2
  val paramList01 = List(
    Params("A1", 0, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A2", 1, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A3", 2, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A4", 3, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),

    Params("C1", 2, 2, 0.0000001, 4000, "instable alpha too great", List(5, 1000, 2000, 3000, 4000)),
    Params("C2", 2, 2, 0.00000005, 4000, "theta_1 increasing to 1126. theta_0 0.32", List(5, 1000, 2000, 3000, 4000)),
    Params("C3", 2, 2, 0.00000001, 4000, "theta_1 increasing to 291. theta_0 0.06", List(5, 1000, 2000, 3000, 4000)),
    Params("C4", 2, 2, 0.000000001, 4000, "theta_1 increasing to 30.94", List(5, 1000, 2000, 3000, 4000)),
    Params("C5", 2, 2, 0.0000000001, 4000, "theta_1 increasing to 3.11", List(5, 1000, 2000, 3000, 4000)),
    Params("C6", 2, 2, 0.00000000001, 4000, "theta_1 increasing to 0.31", List(5, 1000, 2000, 3000, 4000))
  )


  // Grade 1
  val paramList03 = List(
    Params("G10", 0, 1, 0.0001, 40, "", List(2, 5, 10, 40)),
    Params("G11", 1, 1, 0.0001, 40, "", List(2, 5, 10, 40)),
    Params("G12", 2, 1, 0.0001, 40, "", List(2, 5, 10, 40))
  )

  // Grade 2
  val paramList04 = List(
    Params("G20", 0, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000)),
    Params("G21", 1, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000)),
    Params("G22", 2, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000))
  )

  // Grade 3
  val paramList02 = List(
    Params("G30", 0, 3, 0.000000000001, 400, "", List(5, 8, 20, 400)),
    Params("G31", 1, 3, 0.00000000001, 400, "", List(5, 8, 20, 400)),
    Params("G32", 2, 3, 0.00000000001, 400, "", List(5, 8, 20, 400))
  )


  val datasets = List(
    (10, "poly_10.txt"),
    (50, "poly_50.txt"),
    (100, "poly_100.txt"),
    (1000, "poly_1000.txt")
  )

  val random = new java.util.Random()

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  def poly(x: Double)(theta: DenseVector[Double]): Double = {
    val exp = DenseVector.range(0, theta.length)
    exp.map(e => math.pow(x, e.toDouble)).t * theta
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
      val (x, y) = VerificationUtil.readDataSet(fileName)
      val x1 = VerificationUtil.polyExpand(x, params.grade)
      val thetaList = steps(x1, y, params.alpha).take(params.steps).toList

      val tl = thetaList.map(_.toArray).map(a => common.Formatter.formatLimitated(a))
      println(tl.mkString("\n"))
    }

    printThetas(Params("D4", 2, 1, 0.0001, 40, "tryout grade 4", List(2, 5, 10, 40)))

  }

  def regerssionPlotData(): Unit = {

    def plot(params: Params): Unit = {
      val (dataCount, fileName) = datasets(params.datasetIndex)
      val (x, y) = VerificationUtil.readDataSet(fileName)
      val x1 = VerificationUtil.polyExpand(x, params.grade)
      val dataRows = steps(x1, y, params.alpha)
        .take(params.steps)
        .toList
        .zipWithIndex
        .filter { case (_, i) => params.stepsOfInterest.contains(i + 1) }
        .map { case (theta, i) => createDataRow(i, theta) }
      val originalData = createOriginalDataRow(params)
      val dia = Viz.Diagram(
        id = s"result_poly_${params.id}",
        title = f"polinomial regression grade ${params.grade} #data: ${dataCount} ${params.description}",
        dataRows = originalData :: dataRows
      )
      Viz.createDiagram(dia)
    }

    def createOriginalDataRow(params: Params): Viz.DataRow = {
      val (dataCount, fileName) = datasets(params.datasetIndex)
      val (x, y) = VerificationUtil.readDataSet(fileName)
      val data = x.toArray
        .zip(y.toArray)
        .map { case (x, y) => Viz.XY(x, y) }
      val paramStr = common.Formatter.formatLimitatedDense(thetaOrig.toArray)
      Viz.DataRow(
        s"original params:[$paramStr]",
        Viz.Style_POINTS,
        data
      )
    }

    def createDataRow(step: Int, theta: DenseMatrix[Double]): Viz.DataRow = {
      val data = (-100.0 to(100.0, 5))
        .map { x => Viz.XY(x, poly(x)(theta.toDenseVector)) }
      val paramStr = common.Formatter.formatLimitatedDense(theta.toArray)
      Viz.DataRow(f"step: ${step + 1}%3d params:[$paramStr]", data = data)
    }

    paramList03 ::: paramList02 ::: paramList04 foreach(plot(_))


  }

  def createData(): Unit = {

    def polyRandomized(x: Double, deviation: Double)(theta: DenseVector[Double]): Double = {
      val ran = random.nextDouble() * 2.0 * deviation - deviation
      poly(x)(theta) + ran
    }


    sizes.foreach { size =>
      val steps = (max - min) / size
      val id = s"poly_$size"
      val file = Util.dataFile(s"$id.txt")
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, polyRandomized(x, stdDev)(thetaOrig)) }
      Util.writeToFile(file, { pw =>
        val data = ys.map {
          case (x, y) =>
            pw.println(Formatter.format(x, y))
            Viz.XY(x, y)
        }
        val thetaStr = Formatter.format(thetaOrig.toArray)
        val dr = Viz.DataRow(thetaStr, style = Viz.Style_POINTS, data = data)
        val dia = Viz.Diagram(id, s"Polynom datasize=$size", dataRows = List(dr))
        Viz.createDiagram(dia)
      })
      println(s"wrote data to $file")
    }
  }


}

object MainPoliRegerssionPrintData extends App {

  GradientDescentPolinomial.regerssionPrintTheta()

}

object MainPoliRegerssionPlotData extends App {

  GradientDescentPolinomial.regerssionPlotData()

}

object MainPoliCreateData extends App {

  GradientDescentPolinomial.createData()

}

object MainPolyTryout extends App {

  import breeze.linalg._
  import breeze.numerics._

  val x = DenseMatrix((1.0, 2.0, 3.0), (2.2, 2.3, 2.4)).t
  val grade = 1
  val x1 = VerificationUtil.polyExpand(x, grade)

  println(s"grade=$grade")
  println("------------x-\n" + x)
  println("------------x1-\n" + x1)
}

