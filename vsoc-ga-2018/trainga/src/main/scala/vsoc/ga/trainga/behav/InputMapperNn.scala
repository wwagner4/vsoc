package vsoc.ga.trainga.behav

import vsoc.behaviour.Sensors

trait InputMapperNn {

  def mapSensors(sens: Sensors): Option[Array[Double]]

}
