package vsoc.ga.matches.behav

import java.util.Optional

import atan.model.Player
import vsoc.behaviour.{Behaviour, Sensors}
import vsoc.ga.trainga.nn.NeuralNet

class BehaviourNeuralNet(
                          val neuralNet: NeuralNet,
                          val child: Optional[Behaviour],
                          val inputMapper: InputMapperNn,
                          val outputMapper: OutputMapperNn) extends Behaviour {

  var in: Option[Array[Double]] = None

  override def shouldBeApplied(sens: Sensors): Boolean = {
    in = inputMapper.mapSensors(sens)
    in.isDefined
  }

  override def apply(sens: Sensors, player: Player): Unit = {

    val inArray = in.get
    val out: Array[Double] = neuralNet.output(inArray)


    outputMapper.applyOutput(player, out)
  }

  def __apply(sens: Sensors, player: Player): Unit = {
    val out: Array[Double] = neuralNet.output(in.get)
    outputMapper.applyOutput(player, out)
  }

  override def getChild: Optional[Behaviour] = child
}
