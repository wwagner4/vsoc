package vsoc.ga.genetic

trait FitnessFunction[S] {

  def fitness(score: S): Double

}
