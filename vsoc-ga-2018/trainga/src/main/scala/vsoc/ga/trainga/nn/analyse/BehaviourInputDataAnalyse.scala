package vsoc.ga.trainga.nn.analyse

import java.util.Optional

import atan.model.Player
import vsoc.behaviour.{Behaviour, Sensors}
import vsoc.ga.trainga.behav.InputMapperNn

class BehaviourInputDataAnalyse(
                          val inHandler: InputDataHandler,
                          val child: Behaviour,
                          val inputMapper: InputMapperNn) extends Behaviour {

  override def shouldBeApplied(sens: Sensors): Boolean = {
  val inOpt: Option[Array[Double]] = inputMapper.mapSensors(sens)
  inOpt.foreach(in => inHandler.handle(in))
  false
}

  override def apply(sens: Sensors, player: Player): Unit = {
  throw new IllegalStateException("should never be called")
}

  override def getChild: Optional[Behaviour] = Optional.of(child)
}
