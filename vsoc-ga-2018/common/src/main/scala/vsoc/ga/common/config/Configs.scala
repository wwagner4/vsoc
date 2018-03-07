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

  def bobKicks002: Config = new Config {

    override def id: String = "bobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob004"),
      ConfigTrainGa("trainGaKicks01", "bob005"),
      ConfigTrainGa("trainGaKicks01", "bob006"),
    )
  }

  def bob001: Config = new Config {

    override def id: String = "bobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "bob001"),
      ConfigTrainGa("trainGa01", "bob002"),
      ConfigTrainGa("trainGa01", "bob003"),
      ConfigTrainGa("trainGa01", "bob004"),
    )
  }

  def wal001: Config = new Config {

    override def id: String = "bobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "wal001"),
      ConfigTrainGa("trainGa01", "wal002"),
      ConfigTrainGa("trainGa01", "wal003"),
      ConfigTrainGa("trainGa01", "wal004"),
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


  def allKicks: Config = new Config {

    override def id: String = "allKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(walKicks001, bobKicks001, bobKicks002).flatMap(_.trainings)

  }

  def allBobKicks: Config = new Config {

    override def id: String = "allBobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(bobKicks001, bobKicks002).flatMap(_.trainings)

  }
}