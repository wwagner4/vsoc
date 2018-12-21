package vsoc.ga.trainga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.trainga.analyse.old.CreateAllResourcesB03
import vsoc.ga.trainga.config.{ConfigHelper, ConfigTrainGa}
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB03 extends App {

  val baseDir = ConfigHelper.workDir
  implicit val workDir: Path = CreateAllResourcesB03.create(baseDir).resolve("work")
  GuiPopulationRunner.run(ConfigTrainGa("trainGaB03", "work002"))

}
