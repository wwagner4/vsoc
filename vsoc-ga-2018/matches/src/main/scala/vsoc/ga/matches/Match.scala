package vsoc.ga.matches

import vsoc.server.gui.SimulationChangeListener

trait Match {

  def takeStep(): Unit

  def state: MatchResult

  def addSimListener(listener: SimulationChangeListener)

}
