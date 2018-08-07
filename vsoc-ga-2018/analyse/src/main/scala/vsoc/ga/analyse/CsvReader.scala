package vsoc.ga.analyse

import java.nio.file.{Files, Path}
import java.text.NumberFormat
import java.util.Locale

import vsoc.ga.common.config.ConfigHelper

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
    val trainGaDir = ConfigHelper.workDir.resolve(trainGaId)
    require(Files.isDirectory(trainGaDir), s"$trainGaDir is not a directory")
    files(trainGaDir)
      .flatMap{d =>
        if (Files.isDirectory(d))
          files(d).filter(_.getFileName.toString.toLowerCase.endsWith("csv")) else Seq()}
  }

  def files(path: Path): Seq[Path] = {
    require(Files.isDirectory(path), s"$path must be a directory")
    Files.list(path).iterator().asScala.toSeq
  }

}
