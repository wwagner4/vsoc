package vsoc.ga.trainga.ga.impl.player01

import vsoc.ga.genetic.{PhenoTester, PhenoTesterResult}

class PhenoTesterPlayer01 extends PhenoTester[PhenoPlayer01, Double, DataPlayer01] {

  override def fullDesc: String =
    """Creates two teams from the population of players.
      |For each team tree random players are choosen.
      |Plays a number of matches that each player is at least in two matches
    """.stripMargin

  override def test(phenos: Seq[PhenoPlayer01]): PhenoTesterResult[PhenoPlayer01, DataPlayer01] = {


    val _testedPhenos: Seq[(DataPlayer01, PhenoPlayer01)] = ???

    val _populationScore: DataPlayer01 = ???

    new PhenoTesterResult[PhenoPlayer01, DataPlayer01] {
      override def testedPhenos: Seq[(DataPlayer01, PhenoPlayer01)] = _testedPhenos

      override def populationScore: Option[DataPlayer01] = Some(_populationScore)
    }
  }

}
