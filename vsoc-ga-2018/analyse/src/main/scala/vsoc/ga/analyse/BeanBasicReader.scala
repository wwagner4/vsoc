package vsoc.ga.analyse

import java.nio.file.{Files, Path}
import java.text.NumberFormat
import java.util.Locale

import scala.collection.JavaConverters._

object BeanBasicReader {

  def toInt(str: String): Int = str.toInt

  def toDouble(str: String): Double = {
    NumberFormat.getInstance(Locale.ENGLISH).parse(str).doubleValue()
  }

  def toBean(line: String): BeanBasic = {
    val a = line.split(";")
    BeanBasic(
      a(0),
      a(1),
      toInt(a(2)),
      toDouble(a(3))
    )
  }

  def notHeadline(line: String): Boolean = {
    val re = line.indexOf("iterations") < 0
    re
  }

  def read(path: Path): Seq[BeanBasic] = {
    Files.lines(path)
      .iterator()
      .asScala
      .filter(notHeadline)
      .map(toBean)
      .toSeq
  }

}
