package playerpos

import java.io._

import breeze.linalg._
import common.Util
import machinelearning.GradientDescent._
import machinelearning.HypothesisFunction._
import machinelearning.TrainingSet

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg extends App {

  import DenseMatrix._

  val (x, y) = PlayerposLinReg.readDataFile(common.Util.dataFile("pos04.txt"))
  // Add linear offset 1.0
  val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)

  val ts = TrainingSet(x1, y.toDenseMatrix)
  val thetIni = initialTheta(ts)
  val histIni = ThetHist(thetIni, None)
  val gd = gradientDescent(0.000001)(linearFunc)(ts) _
  val steps = Stream.iterate(histIni) { hist =>
    val thet = gd(hist.actual)
    ThetHist(thet, Some(hist.actual))
  }
  steps.take(100)
    .zipWithIndex
    .foreach { case (hist, i) =>
      hist.previous.foreach{p =>
        val meanDiff = Util.meanDiff(hist.actual, p) * 1e10
        println(f"$i%10d $meanDiff%10.4f")
      }
    }


  def readDataFile(file: File): (Matrix[Double], Matrix[Double]) = {
    val all = csvread(file, separator = ',')
    (all(::, 3 to 44), all(::, 0 to 2))
  }

  case class ThetHist(actual: DenseMatrix[Double], previous: Option[DenseMatrix[Double]])

}
