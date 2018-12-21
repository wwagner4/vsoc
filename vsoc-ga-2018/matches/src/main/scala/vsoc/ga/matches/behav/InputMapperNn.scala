package vsoc.ga.matches.behav

import vsoc.behaviour.Sensors

trait InputMapperNn {

  def mapSensors(sens: Sensors): Option[Array[Double]]

}
