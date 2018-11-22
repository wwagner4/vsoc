package vsoc.ga.trainga.ga.common

import vsoc.ga.common.data.Data02
import vsoc.ga.matches._
import vsoc.ga.trainga.ga.FitnessFunction

import scala.util.Random

object TrainGaUtil {

  def playMatch(t1: Team, t2: Team, fitness: FitnessFunction[Data02], stepsPerMatch: Int): (Data02, Data02) = {
    val m: Match = Matches.of(t1, t2)
    for (_ <- 1 to stepsPerMatch) m.takeStep()
    val matchResult: MatchResult = m.state
    val eastResult = matchResult.teamEastResult
    val westResult = matchResult.teamWestResult

    val d1 = resultToData(eastResult, westResult)
    val d2 = resultToData(westResult, eastResult)

    val s1: Double = fitness.fitness(d1)
    val s2: Double = fitness.fitness(d2)
    (d1.copy(score = s1), d2.copy(score = s2))
  }

  def mean(rs: Seq[Data02]): Data02 = {

    def div(d1: Data02, divisor: Double): Data02 = {
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
    }

    require(rs.nonEmpty)
    div(sum(rs), rs.size)
  }

  private def sum(rs: Seq[Data02]): Data02 = {

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

    rs.reduce { (d1, d2) => sum(d1, d2) }
  }


  def resultToData(ownResult: TeamResult, otherResult: TeamResult): Data02 = {
    val kicks = ownResult.playerResults.map(_.kickCount)
    val kickOut = ownResult.playerResults.map(_.kickOutCount)
    val otherGoals = ownResult.playerResults.map(_.otherGoalCount)
    val ownGoals = ownResult.playerResults.map(_.ownGoalCount)

    def mean(d: Seq[Int]): Double = if (d.isEmpty) 0 else d.sum.toDouble / d.size

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
        ownResult.otherGoalCount + otherResult.ownGoalCount
          - (otherResult.otherGoalCount + ownResult.ownGoalCount)
    )
  }

  def pairs(size: Int, testFactor: Int): Seq[(Int, Int)] = {

    def contaisEqual(pairs: Seq[(Int, Int)]): Boolean = {
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
