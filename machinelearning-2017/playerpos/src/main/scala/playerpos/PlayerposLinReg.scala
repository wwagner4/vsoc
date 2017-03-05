package playerpos

import java.io._
import breeze.linalg._
import machinelearning.TrainingSet
import common.{Util, VizCreatorGnuplot, Viz}

import DenseMatrix._

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg {

  // define the vic creator
  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val datasets = List(
    (967, "pos_1000.txt"),
    (4384, "pos_5000.txt"),
    (8899, "pos_10000.txt"),
    (43814, "pos_50000.txt")
  )

  object TestsetError {



    def run(): Unit = {
      val trainingSet = datasets(3)
      val testset = datasets(0)
      val theta = calculateTheta(trainingSet)
      println(theta)
    }



    private def calculateTheta(trainingSet: (Int, String)): DenseMatrix[Double] = {
      val (_, fileName) = trainingSet
      val file = Util.dataFile(fileName)
      val (x, y) = readDataFile(file)
      val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
      steps(x1, y, 1.25e-6).take(50)(49)
    }

    private def steps(x1: DenseMatrix[Double], y:Matrix[Double], alpha: Double): Stream[DenseMatrix[Double]] = {
      import machinelearning.GradientDescent._
      import machinelearning.HypothesisFunction._

      val ts = TrainingSet(x1, y.toDenseMatrix)
      val thetIni = initialTheta(ts)
      val gd = gradientDescent(alpha)(linearFunc)(ts) _
      Stream.iterate(thetIni) { thet => gd(thet) }
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

      val file = Util.dataFile(fileName)
      val (x, y) = readDataFile(file)
      val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)

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
      val values = steps(x1, y, alpha)
        .take(50)
        .zipWithIndex
        .flatMap { case (hist, i) =>
          hist.previous.map { p =>
            val meanDiff = Util.meanDiff(hist.actual, p) * 1e9
            Viz.XY(i, meanDiff)
          }
        }
      println(s"Creating data for alpha $alpha")
      Viz.DataRow(
        "" + alpha,
        values
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
          val file = Util.dataFile(fileName)
          dataRow(sizeDataset, file)
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

    private def dataRow(sizeDataset: Int, file: File): Viz.DataRow = {
      val (x, y) = readDataFile(file)
      val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
      val values = steps(x1, y, 0.0000001)
        .take(50)
        .zipWithIndex
        .flatMap { case (hist, i) =>
          hist.previous.map { p =>
            val meanDiff = Util.meanDiff(hist.actual, p) * 1e9
            Viz.XY(i, meanDiff)
          }
        }
      println("Creating data for " + sizeDataset)
      Viz.DataRow(
        "" + sizeDataset,
        values
      )
    }
  }

  def steps(x1: DenseMatrix[Double], y:Matrix[Double], alpha: Double): Stream[ThetHist] = {
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


  def readDataFile(file: File): (Matrix[Double], Matrix[Double]) = {
    val all = csvread(file, separator = ',')
    (all(::, 3 to 44), all(::, 0 to 2))
  }

  case class ThetHist(actual: DenseMatrix[Double], previous: Option[DenseMatrix[Double]])

}
