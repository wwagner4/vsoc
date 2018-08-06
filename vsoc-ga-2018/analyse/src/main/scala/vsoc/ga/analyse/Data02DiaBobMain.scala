package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

object Data02DiaBobMain extends App {

  new Data02Dia().createDiaConfig(
    cfg = Configs.bobB01,
    diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP),
  )

}

