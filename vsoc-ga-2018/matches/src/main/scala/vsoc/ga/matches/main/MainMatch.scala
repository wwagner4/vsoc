package vsoc.ga.matches.main

import vsoc.ga.matches.impl._

object MainMatch extends App {

  (1 to 10).par.foreach { _ =>
    val teamA = Teams.createTogglers
    val teamB = Teams.createRandomHelix

    val m = Matches.of(teamA, teamB)
    for (_ <- 1 to 20000) m.takeStep()
    val result = m.state

    synchronized {
      println(s"Finished match '$teamB' (w) against '$teamA' (e)")
      println(MatchResults.formatDefault(result))
    }
  }

}
