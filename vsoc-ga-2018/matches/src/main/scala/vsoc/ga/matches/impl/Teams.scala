package vsoc.ga.matches.impl

import atan.model.Controller
import vsoc.behaviour.{Behaviour, BehaviourController}
import vsoc.ga.matches.Team
import vsoc.ga.matches.impl.Behaviours._

object Teams {

  def createTogglers: Team = new AbstractTeam {

    override val controllers = Seq(
      createController(kickBall(towardsTheBall(randomToggle(remainOnField)))),
      createController(kickBall(towardsTheBall(randomToggle(remainOnField)))),
      createController(kickBall(randomToggle(remainOnField))),
    )

    override def name: String = "Togglers"

    override def toString: String = name

  }

  def createRandomHelix: Team = new AbstractTeam {

    override val controllers = Seq(
      createController(kickBall(randomHelix(remainOnField))),
      createController(kickBall(towardsTheBall(randomHelix(remainOnField)))),
      createController(kickBall(towardsTheBall(randomHelix(remainOnField)))),
    )

    override def name: String = "Random Helix"

    override def toString: String = name

  }

  abstract class AbstractTeam extends Team {

    def controllers: Seq[Controller]

    override def playersCount: Int = controllers.size

    override def controller(i: Int): Controller = controllers(i)

    protected def createController(behav: Behaviour): BehaviourController = {
      new BehaviourController(behav)
    }

  }
}

