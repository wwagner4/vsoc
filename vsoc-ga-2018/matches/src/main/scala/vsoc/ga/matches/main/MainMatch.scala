package vsoc.ga.matches.main

import vsoc.ga.matches.impl._

object MainMatch extends App {

  val teamA = Teams.createSparringTeamA
  val teamB = Teams.createSparringTeamA

  val result = Matches.createMatch(teamA, teamB).play

  println(s"Finished match $teamA and $teamB. $result")

}
