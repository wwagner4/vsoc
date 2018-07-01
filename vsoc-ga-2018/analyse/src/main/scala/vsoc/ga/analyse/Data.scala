package vsoc.ga.analyse

case class Data01(
                   trainGaId: String,
                   trainGaNr: String,
                   iterations: Int,
                   score: Double,
                 )

case class Data02(
                   trainGaId: String,
                   trainGaNr: String,
                   iterations: Int,
                   kicksMax: Int,
                   kicksMean: Int,
                   kicksMin: Int,
                   otherGoalsMax: Int,
                   otherownGoalsMean: Int,
                   otherownGoalsMin: Int,
                   ownGoalsMax: Int,
                   ownGoalsMean: Int,
                   ownGoalsMin: Int,
                   goalDifference: Int,
                   score: Double,
                 )
