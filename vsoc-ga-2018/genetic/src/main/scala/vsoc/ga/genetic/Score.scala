package vsoc.ga.genetic

trait Score[S <: Score[S]] {

  def score: Double

  def sum(score1: S, score2: S): S

  def div(score: S, divisor: Double): S

}
