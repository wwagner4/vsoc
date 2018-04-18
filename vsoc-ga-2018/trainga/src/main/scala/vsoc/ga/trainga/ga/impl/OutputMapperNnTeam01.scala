package vsoc.ga.trainga.ga.impl

import atan.model.Player
import vsoc.ga.trainga.behav.OutputMapperNn

/**
  * Output mapper based on the results of NN InputDataAnalyse
  */
class OutputMapperNnTeam01 extends OutputMapperNn {

  override def applyOutput(player: Player, out: Array[Double]): Unit = {

    val dashPower = math.max((out(0) * 25 + 15).toInt, 0)
    val kickPower = math.max((out(1) * 25 + 15).toInt, 0)
    val kickDir = minMax90(out(2) * 40)
    val turn = minMax90(out(3) * 40)

    player.dash(dashPower)
    player.kick(kickPower, kickDir)
    player.turn(turn)
  }

  def minMax90(v: Double): Double = {
    math.max(math.min(v, 90), -90)
  }
}
