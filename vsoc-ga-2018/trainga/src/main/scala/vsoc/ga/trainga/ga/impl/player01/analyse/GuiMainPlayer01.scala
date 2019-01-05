package vsoc.ga.trainga.ga.impl.player01.analyse

import java.nio.file.Path

import vsoc.ga.trainga.config.{ConfigHelper, ConfigTrainGa}
import vsoc.ga.trainga.gui.GuiPopulationRunner

object GuiMainPlayer01 extends App {

  implicit val wd: Path = ConfigHelper.workDir
  val cfg = ConfigTrainGa("trainGaPlayer01B", "bob003")
   GuiPopulationRunner.run(cfg)

}
