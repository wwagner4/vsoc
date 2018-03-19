package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

object Data01Main extends App {

  val cfg = Configs.bob002
  Data01Dia.run(cfg=cfg, filterFactor = 50, minIter = 20)

}
