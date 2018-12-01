package vsoc.ga.genetic

/**
  * Result of testing one the result of testing a population
  * @tparam S Score data. Some data containing the fitness value of the population and some additional parameters
  *           that where used to calculate that fitness value.
  * @tparam A Type of one parameter genotype parameter. Usually a Double
  */
trait GAResult[S <: Score[S], A] {

  def score: Option[S]

  def newPopulation: Seq[Seq[A]]

}


