package vsoc.ga.trainga.config

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

  def b04Bob: Config = new Config {

    override def id: String = "b04Bob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB04", "bob001"),
      ConfigTrainGa("trainGaB04", "bob002"),
      ConfigTrainGa("trainGaB04", "bob003"),
      ConfigTrainGa("trainGaB04", "bob004"),
    )

    override def fullDesc: String =
      """Four populations using TrainGa B04"""
  }

  def b04Work: Config = new Config {

    override def id: String = "b04work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB04", "work001"),
      ConfigTrainGa("trainGaB04", "work002"),
      ConfigTrainGa("trainGaB04", "work003"),
      ConfigTrainGa("trainGaB04", "work004"),
      ConfigTrainGa("trainGaB04", "work005"),
      ConfigTrainGa("trainGaB04", "work006"),
    )

    override def fullDesc: String =
      """Six populations using TrainGa B04"""
  }

  def b05Bob: Config = new Config {

    override def id: String = "b05Bob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB05", "bob001"),
      ConfigTrainGa("trainGaB05", "bob002"),
      ConfigTrainGa("trainGaB05", "bob003"),
      ConfigTrainGa("trainGaB05", "bob004"),
    )

    override def fullDesc: String =
      """Four populations using TrainGa B05"""
  }

  def b05Work: Config = new Config {

    override def id: String = "b05work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB05", "work001"),
      ConfigTrainGa("trainGaB05", "work002"),
      ConfigTrainGa("trainGaB05", "work003"),
      ConfigTrainGa("trainGaB05", "work004"),
      ConfigTrainGa("trainGaB05", "work005"),
      ConfigTrainGa("trainGaB05", "work006"),
    )

    override def fullDesc: String =
      """Six populations using TrainGa B05"""
  }

  def b05test: Config = new Config {

    override def id: String = "b05test"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaB05", "test001"),
      ConfigTrainGa("trainGaB05", "test002"),
    )

    override def fullDesc: String =
      """Test"""
  }

  def player01test: Config = new Config {

    override def id: String = "player01test"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01Simple", "test001"),
      ConfigTrainGa("trainGaPlayer01Simple", "test002"),
    )

    override def fullDesc: String =
      "Testconfiguration: To be used during development"
  }

  def player01Btest: Config = new Config {

    override def id: String = "player01Btest"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01B", "test000"),
      ConfigTrainGa("trainGaPlayer01B", "test001"),
    )

    override def fullDesc: String =
      "Testconfiguration: To be used during development"
  }

  def player01bob: Config = new Config {

    override def id: String = "player01bob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01Simple", "bob000"),
      ConfigTrainGa("trainGaPlayer01Simple", "bob001"),
      ConfigTrainGa("trainGaPlayer01Simple", "bob002"),
      ConfigTrainGa("trainGaPlayer01Simple", "bob003"),
    )

    override def fullDesc: String =
      "Player simulation running on bob"
  }

  def player01work: Config = new Config {

    override def id: String = "player01work"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01Simple", "work000"),
      ConfigTrainGa("trainGaPlayer01Simple", "work001"),
      ConfigTrainGa("trainGaPlayer01Simple", "work002"),
      ConfigTrainGa("trainGaPlayer01Simple", "work003"),
      ConfigTrainGa("trainGaPlayer01Simple", "work004"),
      ConfigTrainGa("trainGaPlayer01Simple", "work005"),
    )

    override def fullDesc: String =
      "Player simulation running on work"
  }

  def player01Bwork: Config = new Config {

    override def id: String = "player01Bwork"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01B", "work000"),
      ConfigTrainGa("trainGaPlayer01B", "work001"),
      ConfigTrainGa("trainGaPlayer01B", "work002"),
      ConfigTrainGa("trainGaPlayer01B", "work003"),
      ConfigTrainGa("trainGaPlayer01B", "work004"),
      ConfigTrainGa("trainGaPlayer01B", "work005"),
    )

    override def fullDesc: String =
      "Player simulation running on work"
  }

  def player01Bbob: Config = new Config {

    override def id: String = "player01Bbob"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaPlayer01B", "bob000"),
      ConfigTrainGa("trainGaPlayer01B", "bob001"),
      ConfigTrainGa("trainGaPlayer01B", "bob002"),
      ConfigTrainGa("trainGaPlayer01B", "bob003"),
    )

    override def fullDesc: String =
      "Player simulation running on bob"
  }


}
