package vsoc.ga.trainga.ga.impl

import atan.model.Player
import vsoc.ga.trainga.behav.OutputMapperNn

class OutputMapperNnTeam extends OutputMapperNn {

  override def applyOutput(player: Player, out: Array[Double]): Unit = {

    if (out(0) > 0.0) {
      val i = (out(0) * 100.0).round.toInt
      if (i > 0) player.dash(i)
    }
    if (out(1) >= 0.0) {
      val p = (out(1) * 100.0).round.toInt
      if (p > 0) {
        val d = out(2) * 10.0
        player.kick(p, d)
      }
    }
    if (out(3) != 0.0) {
      val d = out(3) * 10.0
      player.turn(d)
    }
  }
}
