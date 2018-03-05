package vsoc.ga.trainga.thinner

import java.nio.file.{Files, Path, Paths}

import org.slf4j.LoggerFactory
import vsoc.ga.common.config.Config
import vsoc.ga.trainga.thinner.impl.ThinnerFiles

object Thinner {

  private val log = LoggerFactory.getLogger(Thinner.getClass)

  def thinFromConfig(cfg: Config): Unit = {
    var cnt = 0
    cfg.trainings.foreach { c =>
      ThinnerFiles
        .findFiles(cfg.workDirBase.resolve(Paths.get(c.id, c.nr)))
        .foreach { file =>
          Files.delete(file)
          log.info(s"deleted $file")
          cnt += 1
        }
    }
    log.info(s"deleted $cnt files from configuration ${cfg.id}")
  }

  def thinFromTrainGaId(workDirBase: Path, id: String, nr: String): Unit = {
    var cnt = 0
    ThinnerFiles
      .findFiles(workDirBase.resolve(Paths.get(id, nr)))
      .foreach { file =>
        Files.delete(file)
        log.info(s"deleted $file")
        cnt += 1
      }
    log.info(s"deleted $cnt files from $id $nr")
  }

}
