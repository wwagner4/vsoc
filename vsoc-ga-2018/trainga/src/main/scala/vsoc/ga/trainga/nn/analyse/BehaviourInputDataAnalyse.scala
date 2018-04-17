package vsoc.ga.trainga.nn.analyse

import java.util.Optional

import atan.model.Player
import vsoc.behaviour.{Behaviour, Sensors}
import vsoc.ga.trainga.behav.InputMapperNn
import vsoc.ga.trainga.nn.NeuralNet

class BehaviourInputDataAnalyse(
                          val neuralNet: NeuralNet,
                          val child: Behaviour,
                          val inputMapper: InputMapperNn) extends Behaviour {

  override def shouldBeApplied(sens: Sensors): Boolean = {
  val in: Option[Array[Double]] = inputMapper.mapSensors(sens)
  in.foreach(a => println(a.toList))
  false
}

  override def apply(sens: Sensors, player: Player): Unit = {
  throw new IllegalStateException("should never be called")
}

  override def getChild: Optional[Behaviour] = Optional.of(child)
}
