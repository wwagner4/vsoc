package vsoc.ga.matches.impl

import atan.model.Controller
import vsoc.behaviour.{Behaviour, BehaviourController}
import vsoc.ga.matches.Team
import vsoc.ga.matches.impl.Behaviours._

object Teams {

  def createSparringTeamA: Team = new AbstractTeam {

    val controllers = Seq(
      createController(kickBall(towardsTheBall(randomWalkA(remainOnField)))),
      createController(kickBall(towardsTheBall(randomWalkA(remainOnField)))),
      createController(kickBall(randomWalkA(remainOnField))),
    )

    override def toString: String = "SparringTeamA"

  }

  def createSparringTeamB: Team = new AbstractTeam {

    val controllers = Seq(
      createController(kickBall(randomWalkB(remainOnField))),
      createController(kickBall(towardsTheBall(randomWalkB(remainOnField)))),
      createController(kickBall(towardsTheBall(randomWalkB(remainOnField)))),
    )

    override def toString: String = "SparringTeamB"

  }

}

abstract class AbstractTeam extends Team {

  def controllers: Seq[Controller]

  override def playersCount: Int = controllers.size

  override def controller(i: Int): Controller = controllers(i)

  protected def createController(behav: Behaviour): BehaviourController = {
    new BehaviourController(behav)
  }

}
