package playerpos

import java.io.{File, FileReader}

import breeze.linalg._
import breeze.io._

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg extends App {

  import DenseMatrix._

  val (x, y) = PlayerposLinReg.readDataFile(common.Util.dataFile("pos04.txt"))
  val x1 = DenseMatrix.horzcat(zeros[Double](x.rows, 1), x)


  println(s"x1\n$x1")
  println(s"y\n$y")


  def readDataFile(file: File): (Matrix[Double], Matrix[Double]) = {
    val all = csvread (file, separator = ',')
    (all(::,3 to 44), all(::,0 to 2))
  }


}
