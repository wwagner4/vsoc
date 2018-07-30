package vsoc.ga.trainga.gui

import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  runWithArgs(args, GuiPopulationRunner.run, GuiPopulationMain.getClass.getSimpleName)

}
