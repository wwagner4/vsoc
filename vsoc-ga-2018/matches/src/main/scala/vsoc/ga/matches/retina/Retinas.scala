package vsoc.ga.matches.retina

import atan.model.Flag
import vsoc.behaviour.{DistDirVision, Sensors}
import vsoc.ga.matches.retina.impl.AbstractRetina

case class Retinas(_activationFactor: Double = 1.0) {


  def ball(_activationOffset: Int, _resolution: Int): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      if (sens.getBall.isPresent) Some(sens.getBall.get()) else None
    }
  }

  def goalOwn(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsGoalOwn.get(flag)
      Option(ddv)
    }
  }

  def penaltyOwn(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsPenaltyOwn.get(flag)
      Option(ddv)
    }
  }

  def cornerOwn(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsCornerOwn.get(flag)
      Option(ddv)
    }
  }

  def cornerOther(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsCornerOther.get(flag)
      Option(ddv)
    }
  }

  def center(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsCenter.get(flag)
      Option(ddv)
    }
  }

  def playerOwn(_activationOffset: Int, _resolution: Int, nr: Int): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getPlayersOwn.get(nr)
      Option(ddv)
    }
  }

  def playerOther(_activationOffset: Int, _resolution: Int, nr: Int): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getPlayersOther.get(nr)
      Option(ddv)
    }
  }

  def penaltyOther(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsPenaltyOther.get(flag)
      Option(ddv)
    }
  }

  def goalOther(_activationOffset: Int, _resolution: Int, flag: Flag): Retina = new AbstractRetina {

    override val resolution: Int = _resolution

    override def activationOffset: Int = _activationOffset

    override def activationFactor: Double = _activationFactor

    override def look(sens: Sensors): Option[DistDirVision] = {
      val ddv = sens.getFlagsGoalOther.get(flag)
      Option(ddv)
    }
  }

}
