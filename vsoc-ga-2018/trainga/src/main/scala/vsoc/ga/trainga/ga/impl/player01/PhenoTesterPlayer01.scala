package vsoc.ga.trainga.ga.impl.player01

import vsoc.ga.genetic.PhenoTester

class PhenoTesterPlayer01 extends PhenoTester[PhenoPlayer01, Double, DataPlayer01] {

  override def fullDesc: String =
    """Creates two teams from the population of players.
      |For each team tree random players are choosen.
      |Plays a number of matches that each player is at least in two matches
    """.stripMargin

  override def test(phenos: Seq[PhenoPlayer01]): Seq[(DataPlayer01, PhenoPlayer01)] = ???

}
