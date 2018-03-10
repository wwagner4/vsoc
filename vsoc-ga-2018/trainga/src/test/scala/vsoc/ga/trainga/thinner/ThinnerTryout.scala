package vsoc.ga.trainga.thinner

import vsoc.ga.common.config.Configs

object ThinnerTryout extends App {

  val cfg = Configs.allBob

  cfg.trainings.foreach{t =>
    Thinner.thinFromTrainGaId(cfg.workDirBase, t.id, t.nr)
  }


}
