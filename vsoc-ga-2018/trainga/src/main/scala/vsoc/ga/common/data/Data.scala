package vsoc.ga.common.data

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
                 ) extends DataBase
