package vsoc.ga.trainga.ga.impl

import vsoc.ga.trainga.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.ga.OutputMappers
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

abstract class TrainGa05Abstract extends TrainGaAbstract {

  override def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

  override def outMapper: OutputMapperNn = OutputMappers.om02

}
