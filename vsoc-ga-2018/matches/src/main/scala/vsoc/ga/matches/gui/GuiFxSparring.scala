package vsoc.ga.matches.gui

import javafx.application.Application
import javafx.stage.Stage

import vsoc.ga.matches.impl.{Matches, Teams}
import vsoc.ga.matches.{Match, Team}

class GuiFxSparring extends Application {

  private val teamA: Team = Teams.createTogglers
  private val teamB: Team = Teams.createRandomHelix
  private val _match: Match = Matches.of(teamA, teamB)

  override def start(stage: Stage): Unit = {
    new GuiFx(stage, _match)
    stage.show()
  }

}
