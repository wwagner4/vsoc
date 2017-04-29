package playerpos

import java.io.PrintWriter
import scala.collection.JavaConverters._

import common.SensToVec
import atan.model.Player
import vsoc.behaviour.BehaviourController
import common.FlagDirectionSensToVector
import atan.model.Controller
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import java.util.Optional

import vsoc.server.{ServerUtil, VsocPlayer}
import common.Formatter

import scala.util.Random

object PlayerposCreateData {

  val rand = new Random

  def createDataFiles(): Unit = {
    import common.Util._

    val sizes = List(20, 1000, 5000, 10000, 50000)

    sizes.foreach{size =>
      val filename = s"pos_$size.txt"
      val file = dataFile(filename)
      writeToFile(file, pw => {
        val srv = ServerUtil.current().createServer(10, 10)
        srv.getPlayers.asScala.foreach { p =>
          val ctrl = PlayerposCreateData.createController(Some(pw))
          p.setController(ctrl)
        }
        val to = size / 20
        for (_ <- 1 to to) {
          srv.takeStep()
        }
      })
      val lcnt = lines(file)
      println(s"wrote $lcnt to $file")
      println(s"""($lcnt, "$filename"),""")
    }
  }


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