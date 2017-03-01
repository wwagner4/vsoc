package playerpos

import java.io.{File, FileReader}

import breeze.linalg._
import breeze.io._

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg {

  def readDataFile(file: File): (Matrix[Double], Matrix[Double]) = {
    val all = csvread (file, separator = ',')
    (all(::,3 to 44), all(::,0 to 2))
  }


}
