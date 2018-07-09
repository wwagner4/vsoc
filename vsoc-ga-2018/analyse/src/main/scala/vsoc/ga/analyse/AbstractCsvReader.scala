package vsoc.ga.analyse

import java.nio.file.{Files, Path}
import java.text.NumberFormat
import java.util.Locale

import scala.collection.JavaConverters._

abstract class AbstractCsvReader[D] {
  def toInt(str: String): Int = str.toInt

  def toDouble(str: String): Double = {
    NumberFormat.getInstance(Locale.ENGLISH).parse(str).doubleValue()
  }

  def notHeadline(line: String): Boolean = {
    val re = line.indexOf("iterations") < 0
    re
  }

  def read(path: Path): Seq[D] = {
    if (Files.notExists(path)) Seq.empty[D]
    else
      Files.lines(path)
        .iterator()
        .asScala
        .filter(notHeadline)
        .map(toBean)
        .toSeq
  }

  def toBean(line: String): D

}
