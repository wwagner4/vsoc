package playerpos

import java.io.PrintWriter

import common.SensToVec
import atan.model.Player
import vsoc.behaviour.BehaviourController
import common.FlagDirectionSensToVector
import atan.model.Controller
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import java.util.Optional

import vsoc.server.VsocPlayer
import common.Formatter

import scala.util.Random

object Playerpos {

  val rand = new Random

  def createController(printWriter: Option[PrintWriter]): Controller = {
    val behav = new Behaviour() {

      val stv: SensToVec = new FlagDirectionSensToVector()

      var cnt = 0

      def apply(sens: Sensors, player: Player): Unit = {

        val in = stv.apply(sens)

        if (cnt % 30 == 0) {
          player.move(ran(-20, 20), ran(-20, 20))
          player.turn(ran(0, 6))
        }
        cnt += 1

        in.foreach { a =>
          val vp = player.asInstanceOf[VsocPlayer]
          val pos = vp.getPosition
          val dir = vp.getDirection
          val line = Formatter.format(pos, dir, a)

          if (printWriter.isDefined) printWriter.get.println(line)
          else println(line)
        }

        player.dash(ran(50, 300))
        player.turn(ran(-30, 30))
      }
      def getChild: Optional[Behaviour] = Optional.empty()
      def shouldBeApplied(sens: Sensors): Boolean = true
    }
    new BehaviourController(behav)
  }

  def ran(from: Int, to: Int): Int = {
    require(from < to)
    val w = to - from
    from + rand.nextInt(w)
  }

}