package vsoc.ga.trainga.nn.analyse

import vsoc.behaviour.Behaviour
import vsoc.ga.matches.behav.InputMappers
import vsoc.ga.matches.{Behaviours, Matches, Teams}

class InputDataModel {

  def run(handler: InputDataHandler, numTests: Int): Unit = {

    def testBehav: Behaviour = {
      val child: Behaviour = Behaviours.randomHelix(Behaviours.remainOnField)
      val inMapper = InputMappers.default
      new BehaviourInputDataAnalyse(handler, child, inMapper)
    }

    val behavsa: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
    val behavsb: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
    val teama = Teams.behaviours(behavsa, "A")
    val teamb = Teams.behaviours(behavsb, "B")
    val _match = Matches.of(teama, teamb)

    for (_ <- 1 to numTests) {
      _match.takeStep()
    }
    handler.close()
  }

}
