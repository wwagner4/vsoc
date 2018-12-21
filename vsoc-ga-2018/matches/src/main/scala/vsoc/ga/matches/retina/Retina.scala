package vsoc.ga.matches.retina

import vsoc.behaviour.Sensors

trait Retina {

  def see(sens: Sensors, activations: Array[Double])

}
