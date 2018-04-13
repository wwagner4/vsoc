package vsoc.ga.analyse

import vsoc.ga.common.config.{ConfigHelper, Configs}

object Data01Main extends App {

  Data01Dia.run(
    cfg = Configs.bob002,
    workDir = ConfigHelper.defaultWorkDir,
  )

}
