package vsoc.ga.trainga.behav

import atan.model.Player

trait OutputMapperNn {

  def applyOutput(player: Player, out: Array[Double]): Unit

}
