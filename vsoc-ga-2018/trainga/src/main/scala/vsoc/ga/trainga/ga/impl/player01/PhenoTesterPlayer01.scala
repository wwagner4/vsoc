package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.ga.genetic.PhenoTester
import vsoc.ga.genetic.impl.UtilGa
import vsoc.ga.matches._

import scala.util.Random

class PhenoTesterPlayer01 extends PhenoTester[PhenoPlayer01, Double, DataPlayer01] {

  private val log = LoggerFactory.getLogger(classOf[PhenoTesterPlayer01])

  val matchSteps = 12000

  override def fullDesc: String =
    """Creates two teams from the population of players.
      |For each team tree random players are choosen.
      |Plays a number of matches that each player is at least in two matches
    """.stripMargin


  override def test(phenos: Seq[PhenoPlayer01]): Seq[(DataPlayer01, PhenoPlayer01)] = {
    val _phenos = phenos.map(p => new _Player(p))
    var cnt = 0
    var minMatch = 0
    while (minMatch < 2) {

      val shuffeled = Random.shuffle(_phenos)

      val p0 = shuffeled(0)
      val p1 = shuffeled(1)
      val p2 = shuffeled(2)

      val p3 = shuffeled(3)
      val p4 = shuffeled(4)
      val p5 = shuffeled(5)

      val teamEast: Team = Teams.behaviours(
        Seq(p0.pheno.behav, p1.pheno.behav, p2.pheno.behav), "")
      val teamWest: Team = Teams.behaviours(
        Seq(p3.pheno.behav, p4.pheno.behav, p5.pheno.behav), "")

      val m = Matches.of(teamEast, teamWest)
      for (_ <- 1 to matchSteps) m.takeStep()
      val result: MatchResult = m.state

      p0.scores = score(result.teamEastResult, 0) :: p0.scores
      p1.scores = score(result.teamEastResult, 1) :: p1.scores
      p2.scores = score(result.teamEastResult, 2) :: p2.scores

      p3.scores = score(result.teamWestResult, 0) :: p3.scores
      p4.scores = score(result.teamWestResult, 1) :: p4.scores
      p5.scores = score(result.teamWestResult, 2) :: p5.scores

      cnt += 1
      minMatch = minMatchesPlayed(_phenos)
      log.info(s"played $cnt matches. min $minMatch")
    }
    _phenos.map(playerToRatedPheno)
  }

  private class _Player(val pheno: PhenoPlayer01) {
    var scores = List.empty[DataPlayer01]
  }

  private def score(teamResult: TeamResult, i: Int): DataPlayer01 = {
    val kicks: Double = teamResult.playerResults(i).kickCount
    val goals: Double = teamResult.playerResults(i).otherGoalCount
    DataPlayer01(kicks = kicks, goals = goals)
  }

  private def minMatchesPlayed(players: Seq[_Player]): Int = players.map(p => p.scores.size).min

  private def playerToRatedPheno(p: _Player): (DataPlayer01, PhenoPlayer01) = {
    require(p.scores.nonEmpty)
    val score = UtilGa.meanScore(p.scores, DataPlayer01Ops)
    (score, p.pheno)
  }

}
