package vsoc.ga.matches.impl

import atan.model.Controller
import vsoc.ga.matches.Team

object Teams {

  def createSparringTeamA: Team = new Team {

    override def playersCount: Int = 3

    override def controller(i: Int): Controller = ???
  }

}
