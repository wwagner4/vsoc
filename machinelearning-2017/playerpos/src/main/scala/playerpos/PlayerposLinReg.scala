package playerpos

import java.io._

import breeze.linalg._
import machinelearning.GradientDescent._
import machinelearning.HypothesisFunction._
import machinelearning.TrainingSet

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg extends App {

  import DenseMatrix._

  val (x, y) = PlayerposLinReg.readDataFile(common.Util.dataFile("pos03.txt"))
  // Add linear offset 1.0
  val x1 = DenseMatrix.horzcat(fill(x.rows, 1)(1.0), x)

  val ts = TrainingSet(x1, y.toDenseMatrix)
  val thetIni = initialTheta(ts)
  val gd = gradientDescent(0.000001)(linearFunc)(ts) _
  val steps = Stream.iterate(thetIni)(thet => gd(thet))
  steps.take(200)
    .zipWithIndex
    .foreach { case (thet, i) =>
      println(f"$i%10d $thet%s")
    }


  def readDataFile(file: File): (Matrix[Double], Matrix[Double]) = {
    val all = csvread(file, separator = ',')
    (all(::, 3 to 44), all(::, 0 to 2))
  }


}
