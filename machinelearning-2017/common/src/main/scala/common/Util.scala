package common

import java.io.{File, PrintWriter}

/**
  * Util functions
  */
object Util {

  val workDirName = "vsoc"

  def writeToFile(file: File, op: (PrintWriter) => Unit): Unit = {
    val pw = new PrintWriter(file)
    try {op(pw)} finally {pw.close()}
  }

  def lines(f: File): Int = io.Source.fromFile(f).getLines.size

  def dir(path: String): File = {
    val home = new File(System.getProperty("user.home"))
    val dir = new File(home, path)
    if (!dir.exists()) dir.mkdirs()
    dir
  }
  def dataDir: File = {
    dir(s"$workDirName/data")
  }

  def scriptsDir: File = {
    dir(s"$workDirName/scripts/img")
    dir(s"$workDirName/scripts")
  }

  def dataFile(fileName: String): File ={
    new File(dataDir, fileName)
  }



}
