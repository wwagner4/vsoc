package vsoc.ga.trainga.behav

import atan.model.Player
import vsoc.ga.trainga.ga.Activation

object OutputMappers {

  def omRaw: OutputMapperNn = (player: Player, out: Array[Double]) => {
    player.dash((out(0) * 100).intValue())
    player.kick((out(1) * 100).intValue(), out(2) * 100)
    player.turn(out(3) * 100)
  }

  def om02: OutputMapperNn = (player: Player, out: Array[Double]) => {
    val d = Activation.sigmoid(0.002)(out(0) * 1000) * 30
    val k = Activation.sigmoid(0.002)(out(1) * 1000) * 30
    val kd = Activation.tanh(0.002)(out(2) * 1000) * 30
    val t = Activation.tanh(0.002)(out(3) * 1000) * 30
    player.dash(d.intValue())
    player.kick(k.intValue(), kd)
    player.turn(t)
  }

  def om02varL: OutputMapperNn = (player: Player, out: Array[Double]) => {
    val d = Activation.sigmoid(0.002)(out(0) * 1000) * 60
    val k = Activation.sigmoid(0.002)(out(1) * 1000) * 60
    val kd = Activation.tanh(0.002)(out(2) * 1000) * 50
    val t = Activation.tanh(0.002)(out(3) * 1000) * 50
    player.dash(d.intValue())
    player.kick(k.intValue(), kd)
    player.turn(t)
  }

}
