package vsoc.ga.trainga.ga.impl.player01

import vsoc.ga.matches.nn.{NeuralNetFactories, NeuralNetFactory}
import vsoc.ga.trainga.ga.{FitnessFunctions, TrainGaFitnessFunction}

class TrainGaPlayer01CRec01 extends TrainGaPlayer01Abstract {

  override def id: String = "trainGaPlayer01C"

  override def fullDesc: String =
    """Training each player individually
      |Better reward for Goals (fitnessFunction dataPlayer01C)
    """.stripMargin

  override protected def fitnessFunction: TrainGaFitnessFunction[DataPlayer01] =
    FitnessFunctions.dataPlayer01C

  override def neuralNetFactory: NeuralNetFactory = NeuralNetFactories.rnn01

}
