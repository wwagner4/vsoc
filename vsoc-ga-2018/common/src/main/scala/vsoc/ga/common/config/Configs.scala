package vsoc.ga.common.config

object Configs {

  def b01Work: Config = new Config {

    override def id: String = "b01Work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB01", "work001"),
      ConfigTrainGa("trainGaB01", "work002"),
      ConfigTrainGa("trainGaB01", "work003"),
      ConfigTrainGa("trainGaB01", "work004"),
    )

    override def fullDesc: String =
      """Four populations using B01"""
  }

  def b02Work: Config = new Config {

    override def id: String = "b02Work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB02", "work001"),
      ConfigTrainGa("trainGaB02", "work002"),
      ConfigTrainGa("trainGaB02", "work003"),
      ConfigTrainGa("trainGaB02", "work004"),
      ConfigTrainGa("trainGaB02", "work005"),
      ConfigTrainGa("trainGaB02", "work006"),
    )

    override def fullDesc: String =
      """Six populations using B02"""
  }

  def b02Bob: Config = new Config {

    override def id: String = "b02Bob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB02", "bob001"),
      ConfigTrainGa("trainGaB02", "bob002"),
      ConfigTrainGa("trainGaB02", "bob003"),
      ConfigTrainGa("trainGaB02", "bob004"),
    )

    override def fullDesc: String =
      """Four populations using TraingGa B02"""
  }

  def b03Work: Config = new Config {

    override def id: String = "b03Work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB03", "work001"),
      ConfigTrainGa("trainGaB03", "work002"),
      ConfigTrainGa("trainGaB03", "work003"),
      ConfigTrainGa("trainGaB03", "work004"),
      ConfigTrainGa("trainGaB03", "work005"),
      ConfigTrainGa("trainGaB03", "work006"),
    )

    override def fullDesc: String =
      """Six populations using TrainGa B03"""
  }

  def b03Bob: Config = new Config {

    override def id: String = "b02Bob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB03", "bob001"),
      ConfigTrainGa("trainGaB03", "bob002"),
      ConfigTrainGa("trainGaB03", "bob003"),
      ConfigTrainGa("trainGaB03", "bob004"),
    )

    override def fullDesc: String =
      """Four populations using TrainGa B03"""
  }


}