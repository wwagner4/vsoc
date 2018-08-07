package vsoc.ga.analyse

import java.nio.file.{Files, Path}
import java.text.NumberFormat
import java.util.Locale

import vsoc.ga.common.config.{ConfigHelper, ConfigTrainGa}

import scala.collection.JavaConverters._

abstract class CsvReader[D] {
  def toInt(str: String): Int = str.toInt

  def toDouble(str: String): Double = {
    NumberFormat.getInstance(Locale.ENGLISH).parse(str).doubleValue()
  }

  def notHeadline(line: String): Boolean = {
    val re = line.indexOf("iterations") < 0
    re
  }

  def read(trainGaId: String): Seq[D] = {
    val files: Seq[Path] = extractCsvFiles(trainGaId)
    files.flatMap(f => read(f))
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

  private def extractCsvFiles(trainGaId: String): Seq[Path] = {
    val trainGaDir = ConfigHelper.homeDir.resolve(trainGaId)
    require(Files.isDirectory(trainGaDir), s"$trainGaDir is not a directory")
    Files.list(trainGaDir).iterator().asScala.toSeq.filter(p => p.getFileName.endsWith("csv"))
  }



}
