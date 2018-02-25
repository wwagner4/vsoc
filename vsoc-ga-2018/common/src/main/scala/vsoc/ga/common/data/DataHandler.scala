package vsoc.ga.common.data

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.file.{Files, OpenOption, Path, StandardOpenOption}
import java.util.Locale

class DataHandler(filePath: Path) {
  require(filePath.isAbsolute, s"$filePath must be absolute")
  if (Files.notExists(filePath.getParent)) Files.createDirectories(filePath.getParent)
  else if (Files.exists(filePath)) require(Files.isWritable(filePath), s"$filePath must be writable")
  else require(!Files.isDirectory(filePath), s"$filePath must not be a directory")

  private val sepa = ";"

  private def fmt(value: Any) = value match {
    case v: Double => "%5.3f" formatLocal (Locale.ENGLISH, v)
    case v: Any => v.toString
  }

  private def writeHeader(br: BufferedWriter, line: Seq[(String, Any)]): Unit = {
    val l: String = (for ((k, _) <- line) yield k).mkString(sepa)
    br.write(f"$l\n")
  }

  private def writeData(br: BufferedWriter, line: Seq[(String, Any)]): Unit = {
    val l: String = (for ((_, v) <- line) yield fmt(v)).mkString(sepa)
    br.write(f"$l\n")
  }

  private def write(opt: OpenOption)(f: BufferedWriter => Unit): Unit = {
    val br: BufferedWriter = Files.newBufferedWriter(filePath, Charset.forName("UTF8"), opt)
    try {
      f(br)
    } finally {
      br.close()
    }
  }

  def writeLine(line: Seq[(String, Any)]): Unit = {
    if (Files.notExists(filePath))
      write(StandardOpenOption.CREATE_NEW) { br =>
        writeHeader(br, line)
        writeData(br, line)
      }
    else
      write(StandardOpenOption.APPEND) { br => writeData(br, line) }
  }

  override def toString: String = s"DataHandler[$filePath]"

}
