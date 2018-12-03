package vsoc.ga.trainga.ga.impl.team

import vsoc.ga.genetic._

/**
  * Result of testing one the result of testing a population
  * @tparam S Score data. Some data containing the fitness value of the population and some additional parameters
  *           that where used to calculate that fitness value.
  * @tparam A Type of one parameter genotype parameter. Usually a Double
  */
trait GaReturnTeam[S <: Score[S], A] {

  def score: Option[S]

  def newPopulation: Seq[Seq[A]]

}
