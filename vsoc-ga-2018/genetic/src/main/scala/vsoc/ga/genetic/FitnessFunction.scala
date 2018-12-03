package vsoc.ga.genetic

trait FitnessFunction[S <: Score[S]] {

  def fitness(score: S): Double

}
