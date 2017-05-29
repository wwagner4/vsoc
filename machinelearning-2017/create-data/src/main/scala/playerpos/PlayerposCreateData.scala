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
import vsoc.server.initial._
import vsoc.util.Vec2D

object PlayerposCreateData {

  def createDataFiles(name: String, sizes: Seq[Int], fPlacement: (Player, Int) => Unit): Unit = {

    sizes.foreach { size =>
      val filename = s"${name}_$size.csv"
      val file = dataFile(filename)
      writeToFile(file, pw => {
        val east = new InitialPlacementNone
        val west = new InitialPlacementAllInCenter(1)
        val srv = ServerUtil.current().createServer(east, west)
        srv.getPlayers.asScala.foreach { p =>
          val ctrl = PlayerposCreateData.createController(Some(pw), fPlacement)
          p.setController(ctrl)
        }
        val to = size
        for (_ <- 1 to to) {
          srv.takeStep()
        }
      })
      val lcnt = lines(file)
      println(s"wrote $lcnt to $file")
      println(s"""($lcnt, "$filename"),""")
    }
  }

  def createController(printWriter: Option[PrintWriter], fPlacePlayer: (Player, Int) => Unit): Controller = {

    val behav = new Behaviour() {

      private val _radToDeg = 180.0 / math.Pi

      val stv: SensToVec = new FlagDirectionSensToVector()

      var cnt = 0

      def apply(sens: Sensors, player: Player): Unit = {

        val vp: VsocPlayer = player.asInstanceOf[VsocPlayer]
        if (vp.isTeamEast) {
          // Creating test data for 'east' players would require some extra transformation
          throw new IllegalArgumentException("Test data can only be created with 'west' players")
        }

        val pos: Vec2D = vp.getPosition
        val dir: Double = radToDeg(vp.getDirection)

        fPlacePlayer(player, cnt)

        val in: Option[Array[Double]] = stv(sens)
        in.foreach { a =>
          val line = format(cnt, pos, dir, a)
          if (printWriter.isDefined) printWriter.get.println(line)
          else println(line)
        }
        cnt += 1
      }

      def radToDeg(rad: Double): Double = {
        val deg = rad * _radToDeg
        val m360 = deg % 360.0
        if (m360 < -180) m360 + 360
        else if (m360 > 180) m360 - 360
        else m360
      }

      def getChild: Optional[Behaviour] = Optional.empty()

      def shouldBeApplied(sens: Sensors): Boolean = true
    }
    new BehaviourController(behav)
  }

  def format(cnt: Int, pos: Vec2D, dir: Double, features: Array[Double]): String = {

    implicit val sepaStr = ","

    val cntStr = Formatter.formatWide(cnt)
    val xStr = Formatter.formatWide(pos.getX)
    val yStr = Formatter.formatWide(pos.getY)
    val dirStr = Formatter.formatWide(dir)
    val featuresStr = Formatter.formatLimitatedWide(features)
    s"$cntStr$sepaStr$xStr$sepaStr$yStr$sepaStr$dirStr$sepaStr$featuresStr"
  }

}

object Placement {

  private val rand = new Random

  def placeControllerRandomWalkFromCenter: (Player, Int) => Unit = {
    case (player, cnt) =>
      if (cnt % 30 == 0) {
        player.move(ran(-20, 20), ran(-20, 20))
        player.turn(ran(0, 6))
      }
      player.dash(ran(50, 300))
      player.turn(ran(-30, 30))
  }

  def placeControllerRandomPos: (Player, Int) => Unit = {
    case (player, _) =>
      player.move(ran(-55, 55), ran(-35, 35))
      player.turn(ran(0, 360))
  }

  def placeControllerStraightFromCenter(direction: Double, dash: Int): (Player, Int) => Unit = {
    case (player, cnt) =>
      if (cnt == 0) {
        player.move(0, 0)
        player.turn(direction)
      } else {
        player.dash(dash)
      }
  }

  private def ran(from: Int, to: Int): Int = {
    require(from < to)
    val w = to - from
    from + rand.nextInt(w)
  }

}