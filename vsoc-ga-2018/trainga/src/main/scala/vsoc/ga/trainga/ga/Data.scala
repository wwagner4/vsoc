package vsoc.ga.trainga.ga

import vsoc.ga.genetic._

trait DataBase {
  def trainGaId: String

  def trainGaNr: String

  def iterations: Int

  def score: Double
}

case class Data01(
                   trainGaId: String,
                   trainGaNr: String,
                   iterations: Int = 0,
                   score: Double = 0.0,
                 ) extends DataBase

case class Data02(
                   trainGaId: String = "",
                   trainGaNr: String = "",
                   iterations: Int = 0,

                   kicksMax: Double = 0.0,
                   kicksMean: Double = 0.0,
                   kicksMin: Double = 0.0,
                   kickOutMax: Double = 0.0,
                   kickOutMean: Double = 0.0,
                   kickOutMin: Double = 0.0,

                   otherGoalsMax: Double = 0.0,
                   otherGoalsMean: Double = 0.0,
                   otherGoalsMin: Double = 0.0,
                   ownGoalsMax: Double = 0.0,
                   ownGoalsMean: Double = 0.0,
                   ownGoalsMin: Double = 0.0,

                   goalDifference: Double = 0.0,

                   score: Double = 0.0,
                 ) extends DataBase with Score[Data02]

object Data02Ops extends ScoreOps[Data02] {

  override def sum(score1: Data02, score2: Data02): Data02 = Data02(
    trainGaId = score1.trainGaId,
    trainGaNr = score1.trainGaNr,
    iterations = score1.iterations,
    kicksMax = score1.kicksMax + score2.kicksMax,
    kicksMean = score1.kicksMean + score2.kicksMean,
    kicksMin = score1.kicksMin + score2.kicksMin,
    kickOutMax = score1.kickOutMax + score2.kickOutMax,
    kickOutMean = score1.kickOutMean + score2.kickOutMean,
    kickOutMin = score1.kickOutMin + score2.kickOutMin,
    otherGoalsMax = score1.otherGoalsMax + score2.otherGoalsMax,
    otherGoalsMean = score1.otherGoalsMean + score2.otherGoalsMean,
    otherGoalsMin = score1.otherGoalsMin + score2.otherGoalsMin,
    ownGoalsMax = score1.ownGoalsMax + score2.ownGoalsMax,
    ownGoalsMean = score1.ownGoalsMean + score2.ownGoalsMean,
    ownGoalsMin = score1.ownGoalsMin + score2.ownGoalsMin,
    goalDifference = score1.goalDifference + score2.goalDifference,
    score = score1.score + score2.score,
  )

  override def div(score: Data02, divisor: Double): Data02 = Data02(
    trainGaId = score.trainGaId,
    trainGaNr = score.trainGaNr,
    iterations = score.iterations,
    kicksMax = score.kicksMax / divisor,
    kicksMean = score.kicksMean / divisor,
    kicksMin = score.kicksMin / divisor,
    kickOutMax = score.kickOutMax / divisor,
    kickOutMean = score.kickOutMean / divisor,
    kickOutMin = score.kickOutMin / divisor,
    otherGoalsMax = score.otherGoalsMax / divisor,
    otherGoalsMean = score.otherGoalsMean / divisor,
    otherGoalsMin = score.otherGoalsMin / divisor,
    ownGoalsMax = score.ownGoalsMax / divisor,
    ownGoalsMean = score.ownGoalsMean / divisor,
    ownGoalsMin = score.ownGoalsMin / divisor,
    goalDifference = score.goalDifference / divisor,
    score = score.score / divisor,
  )

  override def unit: Data02 = Data02()

}
