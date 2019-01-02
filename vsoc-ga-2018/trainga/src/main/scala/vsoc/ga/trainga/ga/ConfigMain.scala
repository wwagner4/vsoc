package vsoc.ga.trainga.ga

import vsoc.ga.trainga.config._

object ConfigMain extends App {

  val cfg = Configs.player01Cbob

  new ConfigRunner().run(cfg)

}
