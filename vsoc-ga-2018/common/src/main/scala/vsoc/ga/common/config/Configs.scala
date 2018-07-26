package vsoc.ga.common.config

object Configs {

  def wallB01: Config = b01("wall")

  def bobB01: Config = b01("bob")

  def workB01: Config = b01("work")

  def b01(env: String): Config = new Config {

    override def id: String = env + "B01"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB", env + "001"),
      ConfigTrainGa("trainGaB", env + "002"),
      ConfigTrainGa("trainGaB", env + "003"),
    )

    override def fullDesc: String =
      """B01 initial test"""
  }

}