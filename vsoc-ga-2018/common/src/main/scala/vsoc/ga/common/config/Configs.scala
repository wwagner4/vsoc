package vsoc.ga.common.config

object Configs {

  def bobKicks001: Config = new Config {

    override def id: String = "bobKicks001"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob001"),
      ConfigTrainGa("trainGaKicks01", "bob002"),
      ConfigTrainGa("trainGaKicks01", "bob003"),
    )

    override def fullDesc: String =
      """Kicks for the host 'bob'
        |bob001 - bob003
        |""".stripMargin
  }

  def bobKicks002: Config = new Config {

    override def id: String = "bobKicks002"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGaKicks01", "bob004"),
      ConfigTrainGa("trainGaKicks01", "bob005"),
      ConfigTrainGa("trainGaKicks01", "bob006"),
    )

    override def fullDesc: String =
      """Kicks for the host 'bob'
        |004 - 006
      """.stripMargin
  }

  def bob001: Config = new Config {

    override def id: String = "bob001"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "bob001"),
      ConfigTrainGa("trainGa01", "bob002"),
      ConfigTrainGa("trainGa01", "bob003"),
      ConfigTrainGa("trainGa01", "bob004"),
    )

    override def fullDesc: String =
      """Full fitness for the host 'bob'
        |001 - 004
      """.stripMargin
  }

  def bob002: Config = new Config {

    override def id: String = "bob002"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa03", "bob001"),
      ConfigTrainGa("trainGa03", "bob002"),
      ConfigTrainGa("trainGa03", "bob003"),
      ConfigTrainGa("trainGa03", "bob004"),
      ConfigTrainGa("trainGa03", "bob005"),
    )

    override def fullDesc: String =
      """5 times trainGa03
      """.stripMargin
  }

  def bob003: Config = new Config {

    override def id: String = "bob003"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01_mS", "bob001"),
      ConfigTrainGa("trainGa01_mS", "bob002"),
      ConfigTrainGa("trainGa01_mL", "bob001"),
      ConfigTrainGa("trainGa01_mL", "bob002"),
    )

    override def fullDesc: String =
      """Small and large mutation rate
      """.stripMargin
  }

  def bob004: Config = new Config {

    override def id: String = "bob004"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01m05", "bob001"),
      ConfigTrainGa("trainGa01m10", "bob001"),
      ConfigTrainGa("trainGa01m50", "bob001"),
    )

    override def fullDesc: String =
      """Mutation rates
        |0.005 0.001 0.0005
      """.stripMargin
  }

  def bob005: Config = new Config {

    override def id: String = "bob005"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01m05", "bob002"),
      ConfigTrainGa("trainGa01m10", "bob002"),
      ConfigTrainGa("trainGa01m50", "bob002"),
    )

    override def fullDesc: String =
      """Mutation rates
        |kick out fixed
        |0.005 0.001 0.0005
      """.stripMargin
  }

  def bob006: Config = new Config {

    override def id: String = "bob006"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofS", "bob001"),
      ConfigTrainGa("trainGa01ofS", "bob002"),
      ConfigTrainGa("trainGa01ofM", "bob001"),
      ConfigTrainGa("trainGa01ofM", "bob002"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 50 5
        |small  10 10 1
      """.stripMargin
  }

  def bob007: Config = new Config {

    override def id: String = "bob007"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofM", "bob001"),
      ConfigTrainGa("trainGa01ofM", "bob002"),
      ConfigTrainGa("trainGa01ofM", "bob003"),
      ConfigTrainGa("trainGa01ofM", "bob004"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 50 5
      """.stripMargin
  }

  /**
    * Does not work at all
    */
  def bob008: Config = new Config {

    override def id: String = "bob008"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofM1", "bob001"),
      ConfigTrainGa("trainGa01ofM1", "bob002"),
      ConfigTrainGa("trainGa01ofM1", "bob003"),
      ConfigTrainGa("trainGa01ofM1", "bob004"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 50 2.5
      """.stripMargin
  }

  def bob009: Config = new Config {

    override def id: String = "bob009"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofM2", "bob001"),
      ConfigTrainGa("trainGa01ofM2", "bob002"),
      ConfigTrainGa("trainGa01ofM2", "bob003"),
      ConfigTrainGa("trainGa01ofM2", "bob004"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 20 5
      """.stripMargin
  }

  def wal001: Config = new Config {

    override def id: String = "wal001"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "wal001"),
      ConfigTrainGa("trainGa01", "wal002"),
      ConfigTrainGa("trainGa01", "wal003"),
      ConfigTrainGa("trainGa01", "wal004"),
    )
    override def fullDesc: String =
      """Full fitness for the host 'wallace'
        |001 - 004
      """.stripMargin
  }

  def work001: Config = new Config {

    override def id: String = "work001"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "w001"),
      ConfigTrainGa("trainGa01", "w002"),
      ConfigTrainGa("trainGa01", "w003"),
      ConfigTrainGa("trainGa01", "w004"),
    )
    override def fullDesc: String =
      """Full fitness for work
        |w001 - w004
      """.stripMargin
  }

  def work002: Config = new Config {

    override def id: String = "work002"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa02", "w001"),
      ConfigTrainGa("trainGa02", "w002"),
      ConfigTrainGa("trainGa02", "w003"),
      ConfigTrainGa("trainGa02", "w004"),
    )
    override def fullDesc: String =
      """Multiple trainGa02
      """.stripMargin
  }

  def work003: Config = new Config {

    override def id: String = "work003"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01_mS", "w001"),
      ConfigTrainGa("trainGa01_mS", "w002"),
      ConfigTrainGa("trainGa01_mM", "w001"),
      ConfigTrainGa("trainGa01_mM", "w002"),
      ConfigTrainGa("trainGa01_mL", "w001"),
      ConfigTrainGa("trainGa01_mL", "w002"),
    )

    override def fullDesc: String =
      """small medium large mutation rate
      """.stripMargin
  }

  def work004: Config = new Config {

    override def id: String = "work004"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01m05", "w001"),
      ConfigTrainGa("trainGa01m10", "w001"),
      ConfigTrainGa("trainGa01m50", "w001"),
    )

    override def fullDesc: String =
      """Mutation rates
        |0.005 0.001 0.0005
      """.stripMargin
  }

  def work005: Config = new Config {

    override def id: String = "work005"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01", "w005"),
      ConfigTrainGa("trainGa01", "w006"),
      ConfigTrainGa("trainGa01", "w007"),
      ConfigTrainGa("trainGa01", "w008"),
    )
    override def fullDesc: String =
      """Full fitness standard values
        |vsoc kickoutCount fixed
        |w005 - w008
      """.stripMargin
  }

  def work006: Config = new Config {

    override def id: String = "work006"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofS", "w001"),
      ConfigTrainGa("trainGa01ofS", "w002"),
      ConfigTrainGa("trainGa01ofM", "w001"),
      ConfigTrainGa("trainGa01ofM", "w002"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 50 5
        |small  10 10 1
      """.stripMargin
  }

  def work007: Config = new Config {

    override def id: String = "work007"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofM", "w001"),
      ConfigTrainGa("trainGa01ofM", "w002"),
      ConfigTrainGa("trainGa01ofM1", "w001"),
      ConfigTrainGa("trainGa01ofM1", "w002"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium M   50 50 5
        |medium M1  50 50 2.5
        |Kickfactor reduced
      """.stripMargin
  }

  def work008: Config = new Config {

    override def id: String = "work008"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa01ofM2", "w001"),
      ConfigTrainGa("trainGa01ofM2", "w002"),
      ConfigTrainGa("trainGa01ofM2", "w003"),
      ConfigTrainGa("trainGa01ofM2", "w004"),
    )

    override def fullDesc: String =
      """Output Factors
        |medium 50 50 2.5
        |Kickfactor reduced
      """.stripMargin
  }

  def bob010: Config = new Config {

    override def id: String = "bob010"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04G0", "bob001"),
      ConfigTrainGa("trainGa04G0", "bob002"),
      ConfigTrainGa("trainGa04K0", "bob001"),
      ConfigTrainGa("trainGa04K0", "bob002"),
    )

    override def fullDesc: String =
      """Changed fitness factors
      """.stripMargin
  }

  def work010: Config = new Config {

    override def id: String = "work010"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04G0", "w001"),
      ConfigTrainGa("trainGa04G0", "w002"),
      ConfigTrainGa("trainGa04K0", "w001"),
      ConfigTrainGa("trainGa04K0", "w002"),
    )

    override def fullDesc: String =
      """Changed fitness factors
      """.stripMargin
  }

  def bob011: Config = new Config {

    override def id: String = "bob011"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04M0", "w001"),
      ConfigTrainGa("trainGa04M0", "w002"),
      ConfigTrainGa("trainGa04M0", "w003"),
      ConfigTrainGa("trainGa04M0", "w004"),
    )

    override def fullDesc: String =
      """Network with two intermediate layers
      """.stripMargin
  }

  def work011: Config = new Config {

    override def id: String = "work011"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04M0", "w001"),
      ConfigTrainGa("trainGa04M0", "w002"),
      ConfigTrainGa("trainGa04M0", "w003"),
      ConfigTrainGa("trainGa04M0", "w004"),
    )

    override def fullDesc: String =
      """Network with two intermediate layers
      """.stripMargin
  }

  def work012: Config = new Config {

    override def id: String = "work012"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa05", "w001"),
      ConfigTrainGa("trainGa05", "w002"),
      ConfigTrainGa("trainGa05", "w003"),
      ConfigTrainGa("trainGa05", "w004"),
    )

    override def fullDesc: String =
      """New output weights based on NN analyse
      """.stripMargin
  }

  def bob012: Config = new Config {

    override def id: String = "bob12"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04M0om02", "bob001"),
      ConfigTrainGa("trainGa04M0om02", "bob002"),
      ConfigTrainGa("trainGa04M0om02", "bob003"),
      ConfigTrainGa("trainGa04M0om02", "bob004"),
    )

    override def fullDesc: String =
      """New output mapper
      """.stripMargin
  }

  def work013: Config = new Config {

    override def id: String = "bob13"

    override def trainings: Seq[ConfigTrainGa] = Seq(
      ConfigTrainGa("trainGa04M0om02", "w001"),
      ConfigTrainGa("trainGa04M0om02", "w002"),
      ConfigTrainGa("trainGa04M0om02", "w003"),
      ConfigTrainGa("trainGa04M0om02", "w004"),
    )

    override def fullDesc: String =
      """New output mapper
      """.stripMargin
  }

}