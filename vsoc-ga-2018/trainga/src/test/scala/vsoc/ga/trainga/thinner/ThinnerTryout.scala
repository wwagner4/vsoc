package vsoc.ga.trainga.thinner

import vsoc.ga.common.config.Configs

object ThinnerTryout extends App {

  val cfg = Configs.bob001

  cfg.trainings.foreach{t =>
    Thinner.thinFromDirTree(cfg.workDirBase)
  }


}
