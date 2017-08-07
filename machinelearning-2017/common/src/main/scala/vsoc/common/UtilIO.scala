package vsoc.common

import java.io.{Closeable, File, PrintWriter}
import java.util.UUID

import scala.io.Source

/**
  * General IO Utillities
  */
object UtilIO {

  /**
    * Return a sub dir inside the parent dir.
    * Create the sub dir if it does not exist
    */
  def dirSub(parent: File, subDirName: String): File = {
    val dir = new File(parent, subDirName)
    if (!dir.exists()) dir.mkdirs()
    dir
  }

  /**
    * Default work directory for 'vsoc'
    */
  def dirWork: File = {
    def dirHome: File = {
      new File(System.getProperty("user.home"))
    }
    dirSub(dirHome, "vsoc")
  }

  /**
    * Default data directory
    */
  def dirData: File = {
    dirSub(dirWork, "data")
  }

  /**
    * Default scripts directory
    */
  def dirScripts: File = {
    dirSub(dirWork, "scripts")
  }

  /**
    * Default directory for images
    */
  def dirImg: File = {
    dirSub(dirWork, "img")
  }

  /**
    * Creates a new tem directory
    */
  def dirTmp: File = {
    val tmpDirName = System.getProperty("java.io.tmpdir")
    val uuid = UUID.randomUUID
    new File(new File(tmpDirName), "ml" + uuid.getMostSignificantBits)
  }

  def lines(f: File): Int = Source.fromFile(f).getLines.size

  def use[T <: Closeable](closeable: T)(f: T => Unit): Unit = {
    try {
      f(closeable)
    }
    finally {
      closeable.close()
    }
  }

}
