package vsoc.ga.trainga.ga.impl

import org.slf4j.LoggerFactory
import vsoc.ga.common.data.Data02
import vsoc.ga.common.describe.PropertiesProvider
import vsoc.ga.genetic.{PhenoTester, PhenoTesterResult}
import vsoc.ga.matches.{MatchResult, Matches, TeamResult}
import vsoc.ga.trainga.ga.FitnessFunction

import scala.util.Random

class PhenoTesterTeam(
                       val ran: Random,
                       fitness: FitnessFunction[Data02],
                       testFactor: Int
                     )
  extends PhenoTester[TeamGa, Data02]
    with PropertiesProvider {

  private val log = LoggerFactory.getLogger(classOf[PhenoTesterTeam])

  def matchSteps: Int = 20000

  override def test(phenos: Seq[TeamGa]): PhenoTesterResult[TeamGa, Data02] = {
    val teamCnt = phenos.size
    log.info(s"testing $teamCnt teams")
    val mrc = new PhenoTesterTeamCollector[Data02]()
    val _pairs: Seq[(Int, Int)] = PhenoTesterTeamUtil.pairs(phenos.size, testFactor)
    for ((i1, i2) <- _pairs) {
      val (d1, d2) = playMatch(phenos(i1), phenos(i2))

      mrc.addResult(i1, d1)
      mrc.addResult(i2, d2)
    }

    val _testedPhenos: Seq[(Data02, TeamGa)] = testedPhenos(mrc, phenos)
    val _testedPhenos1: Seq[(Double, TeamGa)] = _testedPhenos.map { case (data, team) => (data.score, team) }

    new PhenoTesterResult[TeamGa, Data02] {

      override def testedPhenos: Seq[(Double, TeamGa)] = _testedPhenos1

      override def populationScore: Option[Data02] = Some(createPopulationScore(_testedPhenos))
    }
  }

  def playMatch(t1: TeamGa, t2: TeamGa): (Data02, Data02) = {
    val m = Matches.of(t1.vsocTeam, t2.vsocTeam)
    for (_ <- 1 to matchSteps) m.takeStep()
    val matchResult: MatchResult = m.state
    val d1 = PhenoTesterTeamUtil.resultToData(matchResult.teamEastResult, matchResult.teamWestResult)
    val d2 = PhenoTesterTeamUtil.resultToData(matchResult.teamWestResult, matchResult.teamEastResult)
    val s1: Double = fitness.fitness(d1)
    val s2: Double = fitness.fitness(d2)
    log.info("finished match %.2f %.2f" format(s1, s2))
    (d1.copy(score = s1), d2.copy(score = s2))
  }

  def testedPhenos(mcr: PhenoTesterTeamCollector[Data02], phenos: Seq[TeamGa]): Seq[(Data02, TeamGa)] = {
    val results: Map[Int, Seq[Data02]] = mcr.results
    for (i <- phenos.indices) yield {
      val rs: Seq[Data02] = results(i)
      require(rs.nonEmpty)
      val mean = PhenoTesterTeamUtil.mean(rs)
      (mean, phenos(i))
    }
  }

  def createPopulationScore(testedPhenos: Seq[(Data02, TeamGa)]): Data02 = {
    PhenoTesterTeamUtil.mean(testedPhenos.map(p => p._1))
  }

  override def properties: Seq[(String, Any)] = Seq(
    ("matchsteps", matchSteps),
    ("matchfact", testFactor),
  )

  override def fullDesc: String =
    s"""Phenotester playing matches
       |$propsFmt
       | matchfact - defines the amount of matches played. amount of matches = matchfact * population size
    """.stripMargin

}

class PhenoTesterTeamCollector[T] {

  private var map = Map.empty[Int, Seq[T]]

  def addResult(i: Int, result: T): Unit = {
    if (map.contains(i)) {
      map = map + (i -> (map(i) :+ result))
    } else {
      map = map + (i -> Seq(result))
    }
  }

  def results: Map[Int, Seq[T]] = map

}

object PhenoTesterTeamUtil {

  def sum(d1: Data02, d2: Data02): Data02 = {
    d1.copy(
      kicksMax = d1.kicksMax + d2.kicksMax,
      kicksMean = d1.kicksMean + d2.kicksMean,
      kicksMin = d1.kicksMin + d2.kicksMin,
      kickOutMax = d1.kickOutMax + d2.kickOutMax,
      kickOutMean = d1.kickOutMean + d2.kickOutMean,
      kickOutMin = d1.kickOutMin + d2.kickOutMin,
      otherGoalsMax = d1.otherGoalsMax + d2.otherGoalsMax,
      otherGoalsMean = d1.otherGoalsMean + d2.otherGoalsMean,
      otherGoalsMin = d1.otherGoalsMin + d2.otherGoalsMin,
      ownGoalsMax = d1.ownGoalsMax + d2.ownGoalsMax,
      ownGoalsMean = d1.ownGoalsMean + d2.ownGoalsMean,
      ownGoalsMin = d1.ownGoalsMin + d2.ownGoalsMin,
      goalDifference = d1.goalDifference + d2.goalDifference,
      score = d1.score + d2.score
    )
  }

  def sum(rs: Seq[Data02]): Data02 =
    rs.reduce { (d1, d2) => sum(d1, d2) }

  def div(d1: Data02, divisor: Double): Data02 =
    d1.copy(
      kicksMax = d1.kicksMax / divisor,
      kicksMean = d1.kicksMean / divisor,
      kicksMin = d1.kicksMin / divisor,
      kickOutMax = d1.kickOutMax / divisor,
      kickOutMean = d1.kickOutMean / divisor,
      kickOutMin = d1.kickOutMin / divisor,
      otherGoalsMax = d1.otherGoalsMax / divisor,
      otherGoalsMean = d1.otherGoalsMean / divisor,
      otherGoalsMin = d1.otherGoalsMin / divisor,
      ownGoalsMax = d1.ownGoalsMax / divisor,
      ownGoalsMean = d1.ownGoalsMean / divisor,
      ownGoalsMin = d1.ownGoalsMin / divisor,
      goalDifference = d1.goalDifference / divisor,
      score = d1.score / divisor
    )

  def mean(rs: Seq[Data02]): Data02 = {
    require(rs.nonEmpty)
    div(sum(rs), rs.size)
  }

  def resultToData(ownResult: TeamResult, otherResult: TeamResult): Data02 = {
    val kicks = ownResult.playerResults.map(_.kickCount)
    val kickOut = ownResult.playerResults.map(_.kickOutCount)
    val otherGoals = ownResult.playerResults.map(_.otherGoalCount)
    val ownGoals = ownResult.playerResults.map(_.ownGoalCount)

    def mean(d: Seq[Int]) = if (d.isEmpty) 0 else d.sum / d.size


    Data02(
      kicksMax = kicks.max,
      kicksMean = mean(kicks),
      kicksMin = kicks.min,
      kickOutMax = kickOut.max,
      kickOutMean = mean(kickOut),
      kickOutMin = kickOut.min,
      otherGoalsMax = otherGoals.max,
      otherGoalsMean = mean(otherGoals),
      otherGoalsMin = otherGoals.min,
      ownGoalsMax = ownGoals.max,
      ownGoalsMean = mean(ownGoals),
      ownGoalsMin = ownGoals.min,
      goalDifference =
        ownResult.ownGoalCount + otherResult.ownGoalCount
          - (otherResult.ownGoalCount + ownResult.otherGoalCount)
    )
  }

  def pairs(size: Int, testFactor: Int): Seq[(Int, Int)] = {

    def contaisEqual(pairs: Seq[(Int, Int)]):Boolean = {
      !pairs.forall(t => t._1 != t._2)
    }

    require(testFactor >= 1)
    val base = Seq.fill(testFactor)(0 until size).flatten
    def ran: Seq[Int] = Random.shuffle(base)
    val re = base.zip(ran)
    if (contaisEqual(re)) pairs(size, testFactor)
    else re
  }

}

