package vsoc.ga.trainga.retina

import vsoc.behaviour.{DistDirVision, Sensors}
import vsoc.ga.trainga.retina.impl.AbstractRetina

object Retinas {


  def ball: Retina = new AbstractRetina {

    val resolution = 5

    override def look(sens: Sensors): Option[DistDirVision] = {
      if (sens.getBall.isPresent) Some(sens.getBall.get()) else None
    }
  }

}
