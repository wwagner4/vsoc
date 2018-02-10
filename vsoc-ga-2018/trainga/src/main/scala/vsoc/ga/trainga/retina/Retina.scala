package vsoc.ga.trainga.retina

import vsoc.behaviour.Sensors

trait Retina {

  def see(sens: Sensors): Array[Double]

}
