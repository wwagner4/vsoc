package vsoc.ga.trainga.gui

import vsoc.ga.common.config.ConfigHelper

object GuiPopulationTryout extends App {

  val wb = ConfigHelper.defaultWorkDir

  GuiPopulationMain.main(Array(wb.toString, "trainGa01", "bob003"))

}