package vsoc.ga.trainga.gui

import java.nio.file.Paths

object GuiPopulationTryoutBob extends App {

  val wdir = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018")

  GuiPopulationMain.main(Array(wdir.toString, "trainGa01m05", "bob002"))

}