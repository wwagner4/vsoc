package vsoc.ga.genetic

trait Score[S <: Score[S]] {

  def score: Double


}
