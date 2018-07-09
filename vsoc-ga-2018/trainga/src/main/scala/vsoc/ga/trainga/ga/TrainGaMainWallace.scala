package vsoc.ga.trainga.ga

import vsoc.ga.common.config.Configs

object TrainGaMainWallace extends App {

  val cfg = Configs.wallB01
  new ConfigRunner().run(cfg)

}
