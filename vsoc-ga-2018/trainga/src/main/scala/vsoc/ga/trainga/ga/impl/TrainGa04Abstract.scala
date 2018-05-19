package vsoc.ga.trainga.ga.impl

import vsoc.ga.trainga.behav.InputMapperNn
import vsoc.ga.trainga.ga.{FitnessFunction, FitnessFunctions}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

/**
  * Base for ne tests.
  * The parameters of this class are equal to trainGa01ofM2
  */
abstract class TrainGa04Abstract extends TrainGaAbstract {


  override def fitness: FitnessFunction = FitnessFunctions.fitnessConsiderAll01

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  override def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

}
