package common

import vsoc.behaviour.Sensors
import atan.model.Flag
import scala.collection.JavaConverters._
import vsoc.behaviour.DistDirVision

/**
  * Maps vsoc sensors to a vector of doubles
  * The return value is defined whenever a relevant amount of values
  * is set in the output vector
  */
trait SensToVec {

  def apply(sens: Sensors): Option[Array[Double]]

}

/**
  * Returns an activation value for each of the 42 flags of
  * the sserver
  *
  * The direction value (DV) represents a value between about -200 and 200
  * depending in what angele player sees the flag. E.g. 0 means the the
  * flag is in font of the player. -10 means the player sees the
  * flag somewhere left.
  *
  * It might als be (and will be in most cases) that a player des not see a flag at all.
  *
  * The activation values for each flag are as follows
  * - 0 if the flag is not seen
  * - 1000 + DV if the player sees the flag
  *
  * For an overview of where what flag can be found see: https://github.com/wwagner4/vsoc/blob/master/vsoc-2007/atan-2007/doc/pic4.png
  *
  */
class FlagDirectionSensToVector extends SensToVec {

  val offset = 100

  def apply(sens: Sensors): Option[Array[Double]] = {
    val re = new Array[Double](42) // Initialize an array with 42 zeros
    var relevant = false

    def handle(dd: DistDirVision): Double = {
      relevant = true
      dd.getDirection + offset
    }

    sens.getFlagsRight.asScala.foreach {
      case (Flag.FLAG_OWN_50, dirdist) => re(16) = handle(dirdist)
      case (Flag.FLAG_OWN_40, dirdist) => re(17) = handle(dirdist)
      case (Flag.FLAG_OWN_30, dirdist) => re(18) = handle(dirdist)
      case (Flag.FLAG_OWN_20, dirdist) => re(19) = handle(dirdist)
      case (Flag.FLAG_OWN_10, dirdist) => re(20) = handle(dirdist)
      case (Flag.FLAG_OTHER_10, dirdist) => re(21) = handle(dirdist)
      case (Flag.FLAG_OTHER_20, dirdist) => re(22) = handle(dirdist)
      case (Flag.FLAG_OTHER_30, dirdist) => re(23) = handle(dirdist)
      case (Flag.FLAG_OTHER_40, dirdist) => re(24) = handle(dirdist)
      case (Flag.FLAG_OTHER_50, dirdist) => re(25) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsOther.asScala.foreach {
      case (Flag.FLAG_RIGHT_30, dirdist) => re(26) = handle(dirdist)
      case (Flag.FLAG_RIGHT_20, dirdist) => re(27) = handle(dirdist)
      case (Flag.FLAG_RIGHT_10, dirdist) => re(28) = handle(dirdist)
      case (Flag.FLAG_LEFT_10, dirdist) => re(34) = handle(dirdist)
      case (Flag.FLAG_LEFT_20, dirdist) => re(35) = handle(dirdist)
      case (Flag.FLAG_LEFT_30, dirdist) => re(36) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsGoalOther.asScala.foreach {
      case (Flag.FLAG_RIGHT, dirdist) => re(29) = handle(dirdist)
      case (Flag.FLAG_LEFT, dirdist) => re(33) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsPenaltyOther.asScala.foreach {
      case (Flag.FLAG_RIGHT, dirdist) => re(30) = handle(dirdist)
      case (Flag.FLAG_CENTER, dirdist) => re(31) = handle(dirdist)
      case (Flag.FLAG_LEFT, dirdist) => re(32) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsLeft.asScala.foreach {
      case (Flag.FLAG_OWN_50, dirdist) => re(4) = handle(dirdist)
      case (Flag.FLAG_OWN_40, dirdist) => re(3) = handle(dirdist)
      case (Flag.FLAG_OWN_30, dirdist) => re(2) = handle(dirdist)
      case (Flag.FLAG_OWN_20, dirdist) => re(1) = handle(dirdist)
      case (Flag.FLAG_OWN_10, dirdist) => re(0) = handle(dirdist)
      case (Flag.FLAG_OTHER_10, dirdist) => re(41) = handle(dirdist)
      case (Flag.FLAG_OTHER_20, dirdist) => re(40) = handle(dirdist)
      case (Flag.FLAG_OTHER_30, dirdist) => re(39) = handle(dirdist)
      case (Flag.FLAG_OTHER_40, dirdist) => re(38) = handle(dirdist)
      case (Flag.FLAG_OTHER_50, dirdist) => re(37) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsOwn.asScala.foreach {
      case (Flag.FLAG_RIGHT_30, dirdist) => re(15) = handle(dirdist)
      case (Flag.FLAG_RIGHT_20, dirdist) => re(14) = handle(dirdist)
      case (Flag.FLAG_RIGHT_10, dirdist) => re(13) = handle(dirdist)
      case (Flag.FLAG_LEFT_10, dirdist) => re(7) = handle(dirdist)
      case (Flag.FLAG_LEFT_20, dirdist) => re(6) = handle(dirdist)
      case (Flag.FLAG_LEFT_30, dirdist) => re(5) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsGoalOwn.asScala.foreach {
      case (Flag.FLAG_RIGHT, dirdist) => re(12) = handle(dirdist)
      case (Flag.FLAG_LEFT, dirdist) => re(8) = handle(dirdist)
      case _ => // Nothing to do
    }
    sens.getFlagsPenaltyOwn.asScala.foreach {
      case (Flag.FLAG_RIGHT, dirdist) => re(11) = handle(dirdist)
      case (Flag.FLAG_CENTER, dirdist) => re(10) = handle(dirdist)
      case (Flag.FLAG_LEFT, dirdist) => re(9) = handle(dirdist)
      case _ => // Nothing to do
    }
    if (relevant) Some(re)
    else Option.empty
  }
}