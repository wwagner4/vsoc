package vsoc.ga.common.data

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.file.{Files, OpenOption, Path, StandardOpenOption}
import java.util.Locale

import vsoc.ga.common.UtilReflection

class CsvWriter(filePath: Path) {
  require(filePath.isAbsolute, s"$filePath must be absolute")
  if (Files.notExists(filePath.getParent)) Files.createDirectories(filePath.getParent)
  else if (Files.exists(filePath)) require(Files.isWritable(filePath), s"$filePath must be writable")
  else require(!Files.isDirectory(filePath), s"$filePath must not be a directory")

  private val sepa = ";"

  private def fmt(value: Any):String = value match {
    case v: Double => "%5.3f" formatLocal (Locale.ENGLISH, v)
    case v: Any => v.toString
  }

  private def writeHeader(br: BufferedWriter, line: AnyRef): Unit = {
    val l: String = UtilReflection.allPropNames(line).mkString(sepa)
    br.write(f"$l\n")
  }

  private def writeData(br: BufferedWriter, line: AnyRef): Unit = {
    val l: String = UtilReflection.allPropValues(line).map(fmt).mkString(sepa)
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

  def writeLine(line: AnyRef): Unit = {
    if (Files.notExists(filePath))
      write(StandardOpenOption.CREATE_NEW) { br =>
        writeHeader(br, line)
        writeData(br, line)
      }
    else
      write(StandardOpenOption.APPEND) { br => writeData(br, line) }
  }

  override def toString: String = s"CsvWriter[$filePath]"

}
