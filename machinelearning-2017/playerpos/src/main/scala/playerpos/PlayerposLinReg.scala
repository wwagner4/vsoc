package playerpos

import java.io.{File, FileReader}

import breeze.linalg._
import breeze.io._

/**
  * Predict player position by using linear regression
  */
object PlayerposLinReg {

  def readDataFile(path: String): (Matrix[Double], Matrix[Double]) = {
    val homeDir = new File(System.getProperty("user.home"))
    val file = new File(homeDir, path)
    val all = csvread (file, separator = ',')
    (all(::,3 to 44), all(::,0 to 2))
  }


}
