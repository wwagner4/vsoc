package vsoc.ga.trainga.ga.impl.player01

import vsoc.ga.genetic.ScoreOps

object DataPlayer01Ops extends ScoreOps[DataPlayer01] {
  override def sum(score1: DataPlayer01, score2: DataPlayer01): DataPlayer01 = {
    require(score1.iterations == score2.iterations)
    DataPlayer01(
      iterations = score1.iterations,
      kicks = score1.kicks + score2.kicks,
      goals = score1.goals + score2.goals,
    )
  }

  override def div(score: DataPlayer01, divisor: Double): DataPlayer01 = {
    DataPlayer01(
      iterations = score.iterations,
      kicks = score.kicks / divisor,
      goals = score.goals / divisor,
    )
  }

  override def unit: DataPlayer01 = DataPlayer01()
}
