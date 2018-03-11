package vsoc.ga.trainga.thinner

import java.nio.file.{Files, Path, Paths}

import org.slf4j.LoggerFactory
import vsoc.ga.common.config.Config
import vsoc.ga.trainga.thinner.impl.ThinnerFiles

import scala.collection.JavaConverters._

object Thinner {

  private val log = LoggerFactory.getLogger(Thinner.getClass)

  def thinFromConfig(cfg: Config): Unit = {
    val cnt = cfg.trainings.map { c =>
      val dir = cfg.workDirBase.resolve(Paths.get(c.id, c.nr))
      thinFromDir(dir)
    }.sum
    log.info(s"deleted $cnt files from configuration ${cfg.id}")
  }

  def thinFromTrainGaId(workDirBase: Path, id: String, nr: String): Unit = {
    val baseDir = workDirBase.resolve(Paths.get(id, nr))
    val cnt = thinFromDir(baseDir)
    log.info(s"deleted $cnt files from $baseDir")
  }

  def thinFromDirTree(baseDir: Path): Unit = {
    require(Files.exists(baseDir))
    require(Files.isDirectory(baseDir))

    def handleFile(file: Path): Int =
      if (Files.isDirectory(file)) thinFromDir(file)
      else 0

    val files = Files.walk(baseDir).iterator().asScala
    val cnt = files.map(handleFile).sum

    log.info(s"deleted $cnt files from $baseDir")
  }

  private def thinFromDir(dir: Path): Int =
    ThinnerFiles.findFiles(dir)
      .map { file =>
        Files.delete(file)
        log.info(s"deleted $file")
        1
      }.sum

}
