package vsoc.ga.matches.gui

import vsoc.ga.matches.{Matches, Teams}

object GuiMain extends App {

  val _match = Matches.of(Teams.ranHelix, Teams.togglers)
  new VsocMatchFrame(_match).setVisible(true)

}
