package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.trainga.analyse.old.CreateAllResourcesB04
import vsoc.ga.trainga.config.{ConfigHelper, ConfigTrainGa}
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB04 extends App {

  val workDir = ConfigHelper.workDir
  val iterBaseDir = CreateAllResourcesB04.create(workDir)
  implicit val iterWorkDir: Path = iterBaseDir.resolve("work")

  GuiPopulationRunner.run(ConfigTrainGa("trainGaB04", "bob004"), None)

}
