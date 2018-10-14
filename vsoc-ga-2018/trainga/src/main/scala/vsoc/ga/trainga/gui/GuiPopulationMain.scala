package vsoc.ga.trainga.gui

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  implicit val workDir: Path = UtilPath.workDir

  runWithArgs(args, GuiPopulationRunner.run, GuiPopulationMain.getClass.getSimpleName)

}
