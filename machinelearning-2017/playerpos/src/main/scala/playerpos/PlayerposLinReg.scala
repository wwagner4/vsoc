package playerpos

import java.io._

import breeze.linalg._
import common.{Util, VizCreatorGnuplot}
import common.Viz._
import machinelearning.TrainingSet

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg {

  import DenseMatrix._

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val dataFiles = List(
    (967, "pos_1000.txt"),
    (4384, "pos_5000.txt"),
    (8899, "pos_10000.txt"),
    (43814, "pos_50000.txt")
  )

  /**
    * Plots the mean squared difference of parametersets
    * on the number of optimisation steps
    * for different sized datasets
    */
  object ThetaDiffOnDataSetSize {

    def plot(): Unit = {
      val drs = dataFiles.map {
        case (sizeDataset, fileName) =>
          val file = Util.dataFile(fileName)
          dataRow(sizeDataset, file)
      }
      val dia = Diagram(
        "thetaconv",
        "theta convergence",
        yLabel = Some("mean squared diff x 10^9"),
        xLabel = Some("number of iterations"),
        yRange = Some(Range(Some(0), Some(2))),
        legendPlacement = LegendPlacement_RIGHT,
        legendTitle = Some("dataset size"),
        dataRows = drs
      )
      createDiagram(dia)
    }

    private def dataRow(sizeDataset: Int, file: File): DataRow = {
      val values = steps(file, 0.0000001)
        .take(50)
        .zipWithIndex
        .flatMap { case (hist, i) =>
          hist.previous.map { p =>
            val meanDiff = Util.meanDiff(hist.actual, p) * 1e9
            XY(i, meanDiff)
          }
        }
      println("Creating data for " + sizeDataset)
      DataRow(
        "" + sizeDataset,
        values
      )
    }
  }

  def steps(file: File, alpha: Double): Stream[ThetHist] = {
    import machinelearning.GradientDescent._
    import machinelearning.HypothesisFunction._

    val (x, y) = readDataFile(file)
    val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)
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
