package vsoc.ga.common.config

import java.nio.file.{Path, Paths}

object Configs {

  def bobKicks001: Config = new Config {

    override def id: String = "bobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob001"),
      ConfigTrainGa("trainGaKicks01", "bob002"),
      ConfigTrainGa("trainGaKicks01", "bob003"),
    )

    override def shortDesc: String = "kicks bob"
    override def fullDesc: String =
      """Kicks for the host 'bob'
        |bob001 - bob003
        |""".stripMargin
  }

  def bobKicks002: Config = new Config {

    override def id: String = "bobKicks002"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob004"),
      ConfigTrainGa("trainGaKicks01", "bob005"),
      ConfigTrainGa("trainGaKicks01", "bob006"),
    )

    override def shortDesc: String = "kicks bob"
    override def fullDesc: String =
      """Kicks for the host 'bob'
        |004 - 006
      """.stripMargin
  }

  def bob001: Config = new Config {

    override def id: String = "bob001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "bob001"),
      ConfigTrainGa("trainGa01", "bob002"),
      ConfigTrainGa("trainGa01", "bob003"),
      ConfigTrainGa("trainGa01", "bob004"),
    )

    override def shortDesc: String = "full fitness bob"
    override def fullDesc: String =
      """Full fitness for the host 'bob'
        |001 - 004
      """.stripMargin
  }

  def bob002: Config = new Config {

    override def id: String = "bob002"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "bob003a"),
      ConfigTrainGa("trainGa01", "bob003b"),
      ConfigTrainGa("trainGa01", "bob003c"),
      ConfigTrainGa("trainGa01", "bob003d"),
    )

    override def shortDesc: String = "full fitness bob"
    override def fullDesc: String =
      """based on duplicates of bob003 after 960 iterations
      """.stripMargin
  }

  def wal001: Config = new Config {

    override def id: String = "wal001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "wal001"),
      ConfigTrainGa("trainGa01", "wal002"),
      ConfigTrainGa("trainGa01", "wal003"),
      ConfigTrainGa("trainGa01", "wal004"),
    )
    override def shortDesc: String = "full fitness wallace"
    override def fullDesc: String =
      """Full fitness for the host 'wallace'
        |001 - 004
      """.stripMargin
  }

  def work001: Config = new Config {

    override def id: String = "work001"

    override def workDirBase: Path = Paths.get("C:\\ta30\\entw1\\work")

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "w001"),
      ConfigTrainGa("trainGa01", "w002"),
    )
    override def shortDesc: String = "full fitness work"
    override def fullDesc: String =
      """Full fitness for work
        |w001 - w002
      """.stripMargin
  }

  def walKicks001: Config = new Config {

    override def id: String = "walKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "004"), // Naming convention was not established then
      ConfigTrainGa("trainGaKicks01", "005"),
      ConfigTrainGa("trainGaKicks01", "006"),
      ConfigTrainGa("trainGaKicks01", "007"),
    )

    override def shortDesc: String = "kicks wallace"
    override def fullDesc: String =
      """kicks from the host 'wallace'
        |004 - 007
      """.stripMargin
  }


  def allKicks: Config = new Config {

    private lazy val sub = Seq(walKicks001, allBobKicks)

    override def id: String = "allKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = sub.flatMap(_.trainings)

    override def shortDesc: String = "kicks multi host"
    override def fullDesc: String = "Kicks from all hosts"
  }

  def allBobKicks: Config = new Config {

    private lazy val sub = Seq(bobKicks001, bobKicks002)

    override def id: String = "allBobKicks001"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = sub.flatMap(_.trainings)

    override def shortDesc: String = "all kicks bob"
    override def fullDesc: String = " All kicking configurations for the host 'bob'"
  }

  def allBob: Config = new Config {

    private lazy val sub = Seq(allBobKicks, bob001)

    override def id: String = "allBob"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = sub.flatMap(_.trainings)

    override def shortDesc: String = "all bob"
    override def fullDesc: String = "All configurations for the host 'bob'"
  }
}