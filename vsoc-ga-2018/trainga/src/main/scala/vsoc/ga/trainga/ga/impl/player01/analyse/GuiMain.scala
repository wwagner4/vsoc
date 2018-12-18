package vsoc.ga.trainga.ga.impl.player01.analyse

import java.nio.file.Path

import vsoc.ga.trainga.config.{ConfigHelper, ConfigTrainGa, Configs}
import vsoc.ga.trainga.gui.GuiPopulationRunner

object GuiMain extends App {

  implicit val wd: Path = ConfigHelper.workDir
  val cfg = ConfigTrainGa("trainGaPlayer01Simple", "bob001")
  GuiPopulationRunner.run(cfg)

}
