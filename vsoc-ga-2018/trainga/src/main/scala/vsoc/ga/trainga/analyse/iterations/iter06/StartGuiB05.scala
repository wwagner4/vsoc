package vsoc.ga.trainga.analyse.iterations.iter06

import java.nio.file.Path

import vsoc.ga.trainga.config.{ConfigHelper, ConfigTrainGa}
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB05 extends App {

  implicit val workDir: Path = ConfigHelper.workDir

  GuiPopulationRunner.run(ConfigTrainGa("trainGaB05", "bob004"))

}
