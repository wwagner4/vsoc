package vsoc.ga.trainga.gui

import java.nio.file.Path

import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner
import vsoc.ga.trainga.config.ConfigHelper

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  implicit val workDir: Path = ConfigHelper.workDir

  runWithArgs(args, GuiPopulationRunner.run, GuiPopulationMain.getClass.getSimpleName)

}
