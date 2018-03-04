package vsoc.ga.common.config

import java.nio.file.Path

object Configs {

  def bobKicks001: Config = new Config {

    override def id: String = "bobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob001"),
      ConfigTrainGa("trainGaKicks01", "bob002"),
      ConfigTrainGa("trainGaKicks01", "bob003"),
    )
  }

  def walKicks001: Config = new Config {

    override def id: String = "walKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "004"),
      ConfigTrainGa("trainGaKicks01", "005"),
      ConfigTrainGa("trainGaKicks01", "006"),
      ConfigTrainGa("trainGaKicks01", "007"),
    )
  }


  def allKicks001: Config = new Config {

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(walKicks001, bobKicks001).flatMap(_.trainings)

    override def id: String = "allKicks001"
  }
}