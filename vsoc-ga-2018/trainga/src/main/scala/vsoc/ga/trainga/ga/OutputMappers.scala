package vsoc.ga.trainga.ga

import atan.model.Player
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
