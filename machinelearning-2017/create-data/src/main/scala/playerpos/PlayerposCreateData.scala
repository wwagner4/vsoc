package playerpos

import java.io.PrintWriter
import java.util.Optional

import atan.model.{Controller, Player}
import vsoc.behaviour.{Behaviour, BehaviourController, Sensors}
import vsoc.server.{ServerUtil, VsocPlayer}

import scala.collection.JavaConverters._
import scala.util.Random
import common.Util._
import common._
import vsoc.util.Vec2D

object PlayerposCreateData {

  val rand = new Random

  def createDataFiles(): Unit = {

    val sizes = List(1000, 5000, 10000, 50000)

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
          val pos: Vec2D = vp.getPosition
          val dir: Double = vp.getDirection
          val line = CreateDataFormatter.format(pos, dir, a)

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