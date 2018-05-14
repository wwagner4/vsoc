package vsoc.ga.common.csv

import java.nio.file._
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import vsoc.ga.common.config.ConfigHelper

import scala.collection.JavaConverters._

object ExportCsv extends App {

  val wd = ConfigHelper.workDir

  val includeSerPopulations = Seq(
    ("trainGa04M0om02varL", "w005"),
    ("trainGa04M0om02varL", "w006"),
    ("trainGa04M0om02varL", "w007"),
    ("trainGa04M0om02varL", "w008"),
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
      val files = Files.list(popDir)
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
