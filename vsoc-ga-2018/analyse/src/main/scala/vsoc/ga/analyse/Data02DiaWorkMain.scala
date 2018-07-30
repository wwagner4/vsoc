package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

object Data02DiaMain extends App {

  new Data02Dia().createDiaConfig(
    cfg = Configs.workB01,
    diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP),
  )

}

