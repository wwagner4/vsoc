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
      ConfigTrainGa("trainGa03", "bob001"),
      ConfigTrainGa("trainGa03", "bob002"),
      ConfigTrainGa("trainGa03", "bob003"),
      ConfigTrainGa("trainGa03", "bob004"),
      ConfigTrainGa("trainGa03", "bob005"),
    )

    override def shortDesc: String = "full fitness bob"
    override def fullDesc: String =
      """5 times trainGa03
      """.stripMargin
  }

  def bob003: Config = new Config {

    override def id: String = "bob003"

    override def workDirBase: Path = ConfigHelper.defaultWorkDir

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01_mS", "bob001"),
      ConfigTrainGa("trainGa01_mS", "bob002"),
      ConfigTrainGa("trainGa01_mM", "bob001"),
      ConfigTrainGa("trainGa01_mM", "bob002"),
    )

    override def shortDesc: String = "full fitness bob"
    override def fullDesc: String =
      """Small and large mutation rate
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
      ConfigTrainGa("trainGa01", "w003"),
      ConfigTrainGa("trainGa01", "w004"),
    )
    override def shortDesc: String = "full fitness work"
    override def fullDesc: String =
      """Full fitness for work
        |w001 - w004
      """.stripMargin
  }

  def work002: Config = new Config {

    override def id: String = "work001"

    override def workDirBase: Path = Paths.get("C:\\ta30\\entw1\\work")

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa02", "w001"),
      ConfigTrainGa("trainGa02", "w002"),
      ConfigTrainGa("trainGa02", "w003"),
      ConfigTrainGa("trainGa02", "w004"),
    )
    override def shortDesc: String = "Multiple trainGa02"
    override def fullDesc: String =
      """Multiple trainGa02
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

}