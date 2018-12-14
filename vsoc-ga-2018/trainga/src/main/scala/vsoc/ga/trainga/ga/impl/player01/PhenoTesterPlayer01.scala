package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.ga.genetic.PhenoTester
import vsoc.ga.matches.{MatchResult, Matches, Team, TeamResult}

class PhenoTesterPlayer01 extends PhenoTester[PhenoPlayer01, Double, DataPlayer01] {

  private val log = LoggerFactory.getLogger(classOf[PhenoTesterPlayer01])

  val matchSteps = 10000

  override def fullDesc: String =
    """Creates two teams from the population of players.
      |For each team tree random players are choosen.
      |Plays a number of matches that each player is at least in two matches
    """.stripMargin

  override def test(phenos: Seq[PhenoPlayer01]): Seq[(DataPlayer01, PhenoPlayer01)] = {
    var _phenos = phenos.map(p => new _Player(p))
    var cnt = 0
    while (minMatchesPlayed(_phenos) < 2) {

      val teamEast: _Team = createTeam(_phenos)
      val teamWest: _Team = createTeam(_phenos)

      val m = Matches.of(teamEast.team, teamWest.team)
      for (_ <- 1 to matchSteps) m.takeStep()
      val result: MatchResult = m.state

      teamEast.player0.scores = score(result.teamEastResult, 0) :: teamEast.player0.scores
      teamEast.player1.scores = score(result.teamEastResult, 1) :: teamEast.player1.scores
      teamEast.player2.scores = score(result.teamEastResult, 2) :: teamEast.player2.scores

      teamWest.player0.scores = score(result.teamWestResult, 0) :: teamWest.player0.scores
      teamWest.player1.scores = score(result.teamWestResult, 1) :: teamWest.player1.scores
      teamWest.player2.scores = score(result.teamWestResult, 2) :: teamWest.player2.scores
      cnt += 1
      log.info(s"played $cnt matches")
    }
    _phenos.map(p => playerToRatedPheno(p))
  }

  def score(teamEastResult: TeamResult, i: Int): DataPlayer01 = ???

  private class _Player(pheno: PhenoPlayer01) {
    var scores = List.empty[DataPlayer01]
  }

  private case class _Team(player0: _Player,
                           player1: _Player,
                           player2: _Player,
                          ) {
    def team: Team = ???
  }

  private def playerToRatedPheno(p: _Player): (DataPlayer01, PhenoPlayer01) = ???

  private def minMatchesPlayed(players: Seq[_Player]): Int = players.map(p => p.scores.size).min


  private def createTeam(players: Seq[_Player]): _Team = {
    require(players.size >= 3)
    val sp: Seq[_Player] = scala.util.Random.shuffle(players)
    _Team(sp(0), sp(1), sp(2))
  }


}
