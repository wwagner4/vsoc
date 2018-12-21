package vsoc.ga.trainga.thinner

import java.nio.file.{Files, Path, Paths}

import org.slf4j.LoggerFactory
import vsoc.ga.trainga.config.ConfigHelper
import vsoc.ga.trainga.thinner.impl.ThinnerFiles

import scala.collection.JavaConverters._

object Thinner {

  private val log = LoggerFactory.getLogger(Thinner.getClass)

  private val workDir = ConfigHelper.workDir

  def thinFromTrainGaId(id: String, nr: String): Unit = {
    val baseDir = workDir.resolve(Paths.get(id, nr))
    val cnt = thinFromDir(baseDir)
    log.info(s"deleted $cnt files from $baseDir")
  }

  def thinFromDirTree(): Unit = {

    def handleFile(file: Path): Int =
      if (Files.isDirectory(file)) thinFromDir(file)
      else 0

    val files = Files.walk(workDir).iterator().asScala
    val cnt = files.map(handleFile).sum

    log.info(s"deleted $cnt files from $workDir")
  }

  private def thinFromDir(dir: Path): Int =
    ThinnerFiles.findFiles(dir)
      .map { file =>
        Files.delete(file)
        log.info(s"deleted $file")
        1
      }.sum

}
