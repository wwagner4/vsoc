package vsoc.ga.matches.main

import vsoc.ga.matches.{MatchResults, Matches, Teams}

object MainMatch extends App {

  (1 to 10).par.foreach { _ =>
    val teamA = Teams.togglers
    val teamB = Teams.ranHelix

    val m = Matches.of(teamA, teamB)
    for (_ <- 1 to 20000) m.takeStep()
    val result = m.state

    synchronized {
      println(s"Finished match '$teamB' (w) against '$teamA' (e)")
      println(MatchResults.formatDefault(result))
    }
  }

}
