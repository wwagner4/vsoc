package vsoc.ga.trainga.ga.impl.player01
import vsoc.ga.trainga.ga.{FitnessFunctions, TrainGaFitnessFunction}

class TrainGaPlayer01B extends TrainGaPlayer01Abstract {

  override def id: String = "trainGaPlayer01A"

  override def fullDesc: String =
    """Training each player individually
      |Better reward for Goals (fitnessFunction dataPlayer01B)
    """.stripMargin

  override protected def fitnessFunction: TrainGaFitnessFunction[DataPlayer01] =
    FitnessFunctions.dataPlayer01B

}
