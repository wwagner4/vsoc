package vsoc.ga.matches.main

import vsoc.ga.matches.impl._

object MainMatch extends App {

  val teamA = Teams.createSparringTeamA
  val teamB = Teams.createSparringTeamA

  val m = Matches.createMatch(teamA, teamB)
  for (_ <- 1 to 2000) m.takeStep()
  val result = m.state

  println(s"Finished match $teamA against $teamB. $result")

}
