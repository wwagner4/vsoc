package common

import java.io.{File, PrintWriter}

import breeze.linalg.{DenseMatrix, sum}

/**
  * Util functions
  */
object Util {

  def writeToFile(file: File, op: (PrintWriter) => Unit): Unit = {
    val pw = new PrintWriter(file)
    try {op(pw)} finally {pw.close()}
  }

  def dir(path: String): File = {
    val home = new File(System.getProperty("user.home"))
    val dir = new File(home, path)
    if (!dir.exists()) dir.mkdirs()
    dir
  }
  def dataDir: File = {
    dir("prj/vsoc/data")
  }

  def scriptsDir: File = {
    dir("prj/vsoc/scripts")
  }

  def dataFile(fileName: String): File ={
    new File(dataDir, fileName)
  }

  def meanDiff(m1: DenseMatrix[Double], m2: DenseMatrix[Double]): Double = {
    val diff = (m1 - m2) ^:^ 2.0
    sum(diff) / diff.size
  }

  def lines(f: File): Int = io.Source.fromFile(f).getLines.size

  def sliceRow(m: DenseMatrix[Double], row: Int): DenseMatrix[Double] = {
    val x1 = m(row, 0 until m.cols)
    DenseMatrix(x1.inner)
  }



}
