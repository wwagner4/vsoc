package vsoc.ga.trainga.nn.analyse

import vsoc.behaviour.Behaviour
import vsoc.ga.matches.{Behaviours, Matches, Teams}
import vsoc.ga.trainga.ga.impl.InputMapperNnTeam
import vsoc.ga.trainga.nn.NeuralNets

object InputDataModelMain extends App {

  println("STARTED")

  val behavsa: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
  val behavsb: Seq[Behaviour] = Seq(testBehav, testBehav, testBehav)
  val teama = Teams.behaviours(behavsa, "A")
  val teamb = Teams.behaviours(behavsb, "B")
  val _match = Matches.of(teama, teamb)

  for (_ <- (1 to 1000)) {
    _match.takeStep()
    sleep(100)
  }
  println("FINISHED")

  private def testBehav: Behaviour = {
    val child: Behaviour = Behaviours.randomHelix(Behaviours.remainOnField)
    val nn = NeuralNets.team01
    val inMapper = new InputMapperNnTeam(1.0)
    new BehaviourInputDataAnalyse(nn, child, inMapper)
  }

  private def sleep(ms: Int): Unit = Thread.sleep(ms)

}
