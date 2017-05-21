package playerpos

import breeze.linalg._
import machinelearning.{HypothesisFunction, TrainingSet}
import common._
import DenseMatrix._

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg {

  // define the viz creator
  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val datasets = List(
    (967, "pos_1000.txt"),
    (4384, "pos_5000.txt"),
    (8899, "pos_10000.txt"),
    (43814, "pos_50000.txt")
  )

  object LearningCurve {

    val yValues = List(
      (0, "pos x"),
      (1, "pos y"),
      (2, "direction")
    )

    def plot(): Unit = {
      val (_, trainFileName) = datasets(3)
      val (testSize, testSetFileName) = datasets(0)
      val theta = calculateTheta(trainFileName, 50)

      val (testX, testY) = readDataSet(testSetFileName)


      val drs = yValues map {
        case (yIndex, name) =>
          val xy = (0 until testSize) map { row =>
            val xRow = BreezeUtil.sliceRow(testX, row)
            val yRow = testY(row, 0 until testY.cols)
            val yCalc = HypothesisFunction.linearFunc(theta)(xRow)
            val v1 = yRow(yIndex)
            val v2 = yCalc(0, yIndex)
            Viz.XY(v1, v2)
          }
          Viz.DataRow(name, style = Viz.Style_POINTS, data = xy)
      }
      val dia = Viz.Diagram("learnqual", "learning quality for linear hypothesis", xLabel = Some("real value"), yLabel = Some("learned value"),  dataRows = drs)
      Viz.createDiagram(dia)
    }

    private def calculateTheta(fileName: String, stepsCnt: Int): DenseMatrix[Double] = {
      val (x1, y) = readDataSet(fileName)
      stepsTheta(x1, y, 1.25e-6).take(stepsCnt)(stepsCnt - 1)
    }

  }

  /**
    * Plots the mean squared difference of theta
    * on optimisation steps
    * for different alphas
    * using a single dataset
    */
  object ThetaDiffOnAlpha {

    def plot(): Unit = {
      // choose one dataset
      val (sizeDataset, fileName) = datasets(3)

      // exponents for alpha.
      val alphas = List(1.25e-6, 1.2e-6, 1.15e-6, 1.1e-6, 1.05e-6, 1.0e-6, 1.0e-7)

      val (x1, y) = readDataSet(fileName)

      val drs = alphas.map {
        alpha =>
          dataRow(x1, y, alpha)
      }
      val dia = Viz.Diagram(
        "thetaconvalpha",
        s"theta convergence on alpha. dataset size $sizeDataset",
        yLabel = Some("mean squared diff x 10^9"),
        xLabel = Some("number of iterations"),
        yRange = Some(Viz.Range(Some(0), Some(200))),
        legendPlacement = Viz.LegendPlacement_RIGHT,
        legendTitle = Some("alpha"),
        dataRows = drs
      )
      Viz.createDiagram(dia)
    }

    private def dataRow(x1: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Viz.DataRow = {
      val values = stepsThetaHist(x1, y, alpha)
        .take(50)
        .zipWithIndex
        .flatMap { case (hist, i) =>
          hist.previous.map { p =>
            val meanDiff = BreezeUtil.meanDiff(hist.actual, p) * 1e9
            Viz.XY(i, meanDiff)
          }
        }
      println(s"Creating data for alpha $alpha")
      Viz.DataRow(
        "" + alpha,
        data = values
      )
    }
  }

  /**
    * Plots the mean squared difference of parametersets
    * on the number of optimisation steps
    * for different sized datasets
    */
  object ThetaDiffOnDataSetSize {

    def plot(): Unit = {
      val drs = datasets.map {
        case (sizeDataset, fileName) =>
          dataRow(sizeDataset, fileName)
      }
      val dia = Viz.Diagram(
        "thetaconv",
        "theta convergence",
        yLabel = Some("mean squared diff x 10^9"),
        xLabel = Some("number of iterations"),
        yRange = Some(Viz.Range(Some(0), Some(10))),
        legendPlacement = Viz.LegendPlacement_RIGHT,
        legendTitle = Some("dataset size"),
        dataRows = drs
      )
      Viz.createDiagram(dia)
    }

    private def dataRow(sizeDataset: Int, fileName: String): Viz.DataRow = {
      val (x1, y) = readDataSet(fileName)
      val values = stepsThetaHist(x1, y, 0.0000001)
        .take(50)
        .zipWithIndex
        .flatMap { case (hist, i) =>
          hist.previous.map { p =>
            val meanDiff = BreezeUtil.meanDiff(hist.actual, p) * 1e9
            Viz.XY(i, meanDiff)
          }
        }
      println("Creating data for " + sizeDataset)
      Viz.DataRow(
        "" + sizeDataset,
        data = values
      )
    }
  }

  case class ThetHist(actual: DenseMatrix[Double], previous: Option[DenseMatrix[Double]])

  def stepsThetaHist(x1: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Stream[ThetHist] = {
    import machinelearning.GradientDescent._
    import machinelearning.HypothesisFunction._

    val ts = TrainingSet(x1, y.toDenseMatrix)
    val thetIni = initialTheta(ts)
    val histIni = ThetHist(thetIni, None)
    val gd = gradientDescent(alpha)(linearFunc)(ts) _
    Stream.iterate(histIni) { hist =>
      val thet = gd(hist.actual)
      ThetHist(thet, Some(hist.actual))
    }
  }

  def stepsTheta(x1: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Stream[DenseMatrix[Double]] = {
    import machinelearning.GradientDescent._
    import machinelearning.HypothesisFunction._

    val ts = TrainingSet(x1, y.toDenseMatrix)
    val thetIni = initialTheta(ts)
    val gd = gradientDescent(alpha)(linearFunc)(ts) _
    Stream.iterate(thetIni) { thet => gd(thet) }
  }

  def readDataSet(fileName: String): (DenseMatrix[Double], DenseMatrix[Double]) = {
    val file = Util.dataFile(fileName)
    val all = csvread(file, separator = ',')
    val x = all(::, 3 to 44)
    val y = all(::, 0 to 2)
    val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
    (x1, y.toDenseMatrix)
  }
}
