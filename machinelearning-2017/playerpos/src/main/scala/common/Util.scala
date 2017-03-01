package common

import java.io.{File, PrintWriter}

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

  def dataFile(fileName: String): File ={
    new File(dataDir, fileName)
  }

}
