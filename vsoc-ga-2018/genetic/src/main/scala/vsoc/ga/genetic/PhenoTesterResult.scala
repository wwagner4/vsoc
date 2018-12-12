package vsoc.ga.genetic

trait PhenoTesterResult[P, S] {

  /**
    * @return Sequence of Phenotypes with their score value
    */
  def testedPhenos: Seq[(S, P)]

  /**
    * TODO Remove no longer needed. can be calculated from 'testedPhenos'
    * @return The mean score of the population
    */
  def populationScore: Option[S]

}

