package vsoc.ga.common.config

object Configs {

  def wallB01: Config = new Config {

    override def id: String = "wallB01"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB", "wall001"),
      ConfigTrainGa("trainGaB", "wall002"),
      ConfigTrainGa("trainGaB", "wall003"),
    )

    override def fullDesc: String =
      """B01 initial test
        |""".stripMargin
  }

  def bobB01: Config = new Config {

    override def id: String = "bobB01"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB", "bob001"),
      ConfigTrainGa("trainGaB", "bob002"),
      ConfigTrainGa("trainGaB", "bob003"),
    )

    override def fullDesc: String =
      """B01 initial test
        |""".stripMargin
  }


}