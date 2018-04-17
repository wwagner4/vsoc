package vsoc.ga.trainga.nn.analyse

import vsoc.behaviour.Behaviour
import vsoc.ga.matches.{Behaviours, Matches, Teams}
import vsoc.ga.trainga.ga.impl.InputMapperNnTeam

class InputDataModel {

  def run(handler: InputDataHandler, numTests: Int): Unit = {

    def testBehav: Behaviour = {
      val child: Behaviour = Behaviours.randomHelix(Behaviours.remainOnField)
      val inMapper = new InputMapperNnTeam(1.0)
      new BehaviourInputDataAnalyse(handler, child, inMapper)
    }

    def sleep(ms: Int): Unit = Thread.sleep(ms)

    println("STARTED")

    val behavsa: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
    val behavsb: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
    val teama = Teams.behaviours(behavsa, "A")
    val teamb = Teams.behaviours(behavsb, "B")
    val _match = Matches.of(teama, teamb)

    for (_ <- 1 to numTests) {
      _match.takeStep()
      sleep(100)
    }
    println("FINISHED")
  }

}
