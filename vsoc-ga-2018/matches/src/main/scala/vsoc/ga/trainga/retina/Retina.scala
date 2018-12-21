package vsoc.ga.trainga.retina

import vsoc.behaviour.Sensors

trait Retina {

  def see(sens: Sensors, activations: Array[Double])

}
