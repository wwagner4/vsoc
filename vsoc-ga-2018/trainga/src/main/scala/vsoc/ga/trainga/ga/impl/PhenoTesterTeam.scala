package vsoc.ga.trainga.ga.impl

import org.slf4j.LoggerFactory
import vsoc.ga.common.describe.PropertiesProvider
import vsoc.ga.genetic.{PhenoTester, PhenoTesterResult}
import vsoc.ga.matches.{Matches, Team, TeamResult}

import scala.util.Random

case class Result(nr: Int, team: TeamGa, matchCount: Int, fitnessSum: Double)

class PhenoTesterTeam(val ran: Random, fitness: (TeamResult) => Double, popMultiplicationFactor: Int) extends PhenoTester[TeamGa, Double] with PropertiesProvider {

  private val log = LoggerFactory.getLogger(classOf[PhenoTesterTeam])

  def meanTeamFitness(results: List[Result], cnt: Int, sum: Double): Double = {
    results match {
      case Nil =>
        if (cnt == 0) throw new IllegalStateException("Could not calculate mean team score as there was no team with any score :(. There is something completely wrong")
        sum / cnt

      case teamResult :: _results =>
        if (teamResult.matchCount == 0) meanTeamFitness(_results, cnt, sum)
        else {
          val teamFit = teamResult.fitnessSum / teamResult.matchCount
          meanTeamFitness(_results, cnt + 1, sum + teamFit)
        }
    }
  }

  def testedPhenos(results: List[Result], meanFitness: Double): List[(Double, TeamGa)] = {
    results match {
      case Nil => Nil
      case r :: _results =>
        if (r.matchCount == 0) {
          log.warn(f"Team ${r.team} played no game. It was ated with the mean fitness of the other teams $meanFitness%.2f")
          (meanFitness, r.team) :: testedPhenos(_results, meanFitness)
        } else {
          val fit = r.fitnessSum / r.matchCount
          (fit, r.team) :: testedPhenos(_results, meanFitness)
        }
    }

  }

  override def test(phenos: Seq[TeamGa]): PhenoTesterResult[TeamGa, Double] = {

    val teamCnt = phenos.size
    log.info(s"testing $teamCnt teams")

    val matchMaxCnt = popMultiplicationFactor * teamCnt

    def updateResults(old: List[Result], fitness: Double, nr: Int): List[Result] = {
      old match {
        case Nil => Nil
        case r :: _old =>
          if (r.nr == nr) r.copy(matchCount = r.matchCount + 1, fitnessSum = r.fitnessSum + fitness) :: updateResults(_old, fitness, nr)
          else r :: updateResults(_old, fitness, nr)
      }
    }

    var results: List[Result] = phenos.toList.zipWithIndex.map { case (t, i) => Result(i, t, 0, 0.0) }
    for (nr <- 1 to matchMaxCnt) {
      val (r1, r2) = RandomElemsPicker.pick(results, ran)
      val (f1, f2) = playMatch(r1.team.vsocTeam, r2.team.vsocTeam, nr, matchMaxCnt)
      synchronized {
        results = updateResults(results, f1, r1.nr)
        results = updateResults(results, f2, r2.nr)
      }
    }

    val meanFitness = meanTeamFitness(results, 0, 0.0)

    val tp = testedPhenos(results, meanFitness)

    new PhenoTesterResult[TeamGa, Double] {
      override def testedPhenos: Seq[(Double, TeamGa)] = tp

      override def score: Option[Double] = Some(meanFitness)
    }
  }

  def matchSteps: Int = 20000

  def playMatch(t1: Team, t2: Team, nr: Int, maxNr: Int): (Double, Double) = {
    val m = Matches.of(t1, t2)
    for (_ <- 1 to matchSteps) m.takeStep()
    val result = m.state
    val t1Fit: Double = fitness(result.teamEastResult)
    val t2Fit: Double = fitness(result.teamWestResult)
    val prog = f"$nr / $maxNr"
    log.info(f"finished match $prog%10s [$t1Fit%10.2f <==> $t2Fit%10.2f]")
    (t1Fit, t2Fit)
  }

  override def properties: Seq[(String, Any)] = Seq(
    ("matchsteps", matchSteps),
    ("matchfact", popMultiplicationFactor),
  )

  override def shortDesc: String = "match playing"

  override def fullDesc: String =
    s"""Phenotester playing matches
       |$propsFmt
       | matchfact - defines the amount of matches played. amount of matches = matchfact * population size
    """.stripMargin
}
