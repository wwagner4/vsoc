package vsoc.ga.matches.gui

import vsoc.ga.matches.{Matches, Teams}

object GuiMain extends App {

  val mf = () => Matches.of(Teams.ranHelix, Teams.togglers)
  new VsocMatchFrame(mf).setVisible(true)

}
