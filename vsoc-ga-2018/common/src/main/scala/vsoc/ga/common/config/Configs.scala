package vsoc.ga.common.config

object Configs {

  def wallB01: Config = b01("wall")

  def bobB01: Config = b01("bob")

  def workB01: Config = b01("work")

  def b01(env: String): Config = new Config {

    override def id: String = env + "B01"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB01", env + "001"),
      ConfigTrainGa("trainGaB01", env + "002"),
      ConfigTrainGa("trainGaB01", env + "003"),
      ConfigTrainGa("trainGaB01", env + "004"),
    )

    override def fullDesc: String =
      """B01 initial test after score fix"""
  }


  def workTrainGa05fitFac03b: Config = new Config {
    override def id: String = "workTrainGa05fitFac03b"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa05fitFac03b", "w001"),
      ConfigTrainGa("trainGa05fitFac03b", "w002"),
      ConfigTrainGa("trainGa05fitFac03b", "w003"),
      ConfigTrainGa("trainGa05fitFac03b", "w004"),
      ConfigTrainGa("trainGa05fitFac03b", "w005"),
      ConfigTrainGa("trainGa05fitFac03b", "w006"),
    )

    override def fullDesc: String = "Initial fitness function"
  }

}