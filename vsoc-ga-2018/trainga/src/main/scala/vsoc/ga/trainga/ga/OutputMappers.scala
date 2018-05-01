package vsoc.ga.trainga.ga

import atan.model.Player
import vsoc.ga.common.act.Activation
import vsoc.ga.trainga.behav.OutputMapperNn

object OutputMappers {

  def om01F(of: OutputFactors): OutputMapperNn = {
    new OutputMapperNnTeam(of)
  }

  def om01FDefault: OutputMapperNn = {
    val of = OutputFactors() // 100 100 10
    new OutputMapperNnTeam(of)
  }

  def om01FSmall: OutputMapperNn = {
    val of = OutputFactors(50, 20, 5) // smaller than orig
    new OutputMapperNnTeam(of)
  }

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

  class OutputMapperNnTeam(factors: OutputFactors) extends OutputMapperNn {

    override def applyOutput(player: Player, out: Array[Double]): Unit = {

      if (out(0) > 0.0) {
        val i = (out(0) * factors.dash).round.toInt
        if (i > 0) player.dash(i)
      }
      if (out(1) >= 0.0) {
        val p = (out(1) * factors.kick).round.toInt
        if (p > 0) {
          val d = out(2) * 10.0
          player.kick(p, d)
        }
      }
      if (out(3) != 0.0) {
        val d = out(3) * factors.turn
        player.turn(d)
      }
    }
  }

}
