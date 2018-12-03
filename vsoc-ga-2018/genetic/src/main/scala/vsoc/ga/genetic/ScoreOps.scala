package vsoc.ga.genetic

trait ScoreOps[S] {

  def sum(score1: S, score2: S): S

  def div(score: S, divisor: Double): S

  def unit: S

}
