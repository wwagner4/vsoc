package vsoc.ga.matches

import atan.model.Controller
import vsoc.behaviour.{Behaviour, BehaviourController}
import vsoc.ga.matches.Behaviours._

object Teams {

  def behaviours(behaviours: Seq[Behaviour], _name: String): Team = new AbstractTeam {

    override val controllers: Seq[BehaviourController] = behaviours.map(createController)

    override def name: String = _name

    override def toString: String = name

  }

  def togglers: Team = new AbstractTeam {

    override val controllers = Seq(
      createController(kickBall(towardsTheBall(randomToggle(remainOnField)))),
      createController(kickBall(towardsTheBall(randomToggle(remainOnField)))),
      createController(kickBall(randomToggle(remainOnField))),
    )

    override def name: String = "Togglers"

    override def toString: String = name

  }

  def ranHelix: Team = new AbstractTeam {

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

