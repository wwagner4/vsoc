package vsoc.ga.trainga.thinner

import vsoc.ga.common.config.Configs

object ThinnerTryout extends App {

  val cfg = Configs.bobKicks001

  val train = cfg.trainings(2)

  Thinner.thinFromTrainGaId(cfg.workDirBase, train.id, train.nr)

}
