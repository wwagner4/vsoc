package vsoc.ga.trainga.ga

import vsoc.ga.trainga.config._

object ConfigMain extends App {

  val cfg = Configs.b05test

  new ConfigRunner().run(cfg)

}
