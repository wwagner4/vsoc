package vsoc.ga.analyse

import vsoc.ga.common.config.{ConfigHelper, Configs}

object Data01Main extends App {

  val cfg = Configs.bob002
  Data01Dia.run(
    cfg=cfg,
    workDir=ConfigHelper.defaultWorkDir,
    filterFactor = 50, minIter = 20)

}
