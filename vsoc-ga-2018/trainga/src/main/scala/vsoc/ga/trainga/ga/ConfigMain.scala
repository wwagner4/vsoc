package vsoc.ga.trainga.ga

import vsoc.ga.trainga.config._

object ConfigMain extends App {

  val cfg = Configs.player01CRec01work
  //val cfg = Configs.player01Cwork

  new ConfigRunner().run(cfg)

}
