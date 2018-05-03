package vsoc.ga.trainga.ga.impl

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.behav.InputMapperNn
import vsoc.ga.trainga.ga.FitnessFunctions
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

/**
  * Base for ne tests.
  * The parameters of this class are equal to trainGa01ofM2
  */
abstract class TrainGa04Abstract extends TrainGaAbstract {


  override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

  override def fitnessDesc: String = "consider all"

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  override def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

}
