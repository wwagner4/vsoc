package vsoc.ga.trainga.behav

import java.util.{Locale, Optional}

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

    def fmt(a: Array[Double]): String = {
      a.map { d: Double =>
        if (d == 0.0) "."
        else "%.1f|".formatLocal(Locale.ENGLISH, d)
      }.mkString("")
    }

    val inArray = in.get
    val out: Array[Double] = neuralNet.output(inArray)


    def debug(): Unit = {
      val nr = player.getNumber
      val t = if (player.isTeamEast) "E" else "W"
      if (t == "W" && nr == 1) {
        println(f"$nr%4d $t in:  ${fmt(inArray)}")
        println(f"$nr%4d $t out: ${fmt(out)}")
        println("")
      }
    }
    //debug
    outputMapper.applyOutput(player, out)
  }

  def __apply(sens: Sensors, player: Player): Unit = {
    val out: Array[Double] = neuralNet.output(in.get)
    outputMapper.applyOutput(player, out)
  }

  override def getChild: Optional[Behaviour] = child
}
