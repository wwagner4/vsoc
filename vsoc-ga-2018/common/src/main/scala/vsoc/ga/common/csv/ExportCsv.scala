package vsoc.ga.common.csv

import java.nio.file._
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import vsoc.ga.common.UtilPath
import vsoc.ga.common.config.ConfigHelper

import scala.collection.JavaConverters._

object ExportCsv extends App {

  val wd = UtilPath.workDir

  val includeSerPopulations = Seq(
    ("trainGa05fitFac01", "w001"),
    ("trainGa05fitFac01", "w002"),
    ("trainGa05fitFac01", "w003"),
    ("trainGa05fitFac02", "w001"),
    ("trainGa05fitFac02", "w002"),
    ("trainGa05fitFac02", "w003"),
  )

  def timestamp = {
    val formatter = DateTimeFormatter.ofPattern("yyMMdd")
    formatter.format(LocalDate.now())
  }

  val ts = timestamp

  val host = ConfigHelper.hostName

  val outDir = Paths.get("target", s"trainga-$host-$ts")

  Files.createDirectories(outDir)


  def processPop(popDir: Path): Unit = {
    if (Files.isDirectory(popDir)) {
      val includeSer = {
        val gaName = popDir.getName(popDir.getNameCount - 2).toString
        val popName = popDir.getName(popDir.getNameCount - 1).toString
        includeSerPopulations.contains((gaName, popName))
      }
      Files.list(popDir)
        .iterator()
        .asScala
        .foreach { f =>
          val cnt = f.getNameCount
          val f1 = f.subpath(cnt - 3, cnt)
          val o1 = outDir.resolve(f1.getParent)
          Files.createDirectories(o1)
          if (f.getFileName.toString.endsWith("csv")) {
            val of = outDir.resolve(f1)
            Files.copy(f, of, StandardCopyOption.REPLACE_EXISTING)
            println(s"copied $f to $o1")
          }
          if (includeSer && f.getFileName.toString.endsWith("ser")) {
            val of = outDir.resolve(f1)
            Files.copy(f, of, StandardCopyOption.REPLACE_EXISTING)
            println(s"copied $f to $o1")
          }
        }
    }
  }

  def processTrainGa(trainGa: Path): Unit = {
    if (trainGa.getFileName.toString.startsWith("train")) {
      Files.list(trainGa).iterator().asScala.foreach(p => processPop(p))
    }
  }

  Files.list(wd).iterator().asScala.foreach(p => processTrainGa(p))

}
