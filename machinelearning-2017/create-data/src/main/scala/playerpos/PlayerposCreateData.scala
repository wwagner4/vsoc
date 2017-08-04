package playerpos

import java.io.PrintWriter
import java.util.Optional

import atan.model.{Controller, Player}
import vsoc.behaviour.{Behaviour, BehaviourController, Sensors}
import vsoc.server.{ServerUtil, VsocPlayer}

import scala.collection.JavaConverters._
import scala.util.Random
import vsoc.common.Util._
import common._
import vsoc.server.initial._
import vsoc.util.Vec2D

object PlayerposCreateData {

  implicit val sepaStr = ";"

  def createDataFiles(name: String, sizes: Seq[Int], fPlacement: (Player, Int) => Unit, initialPlacement: vsoc.server.InitialPlacement): Unit = {

    sizes.foreach { size =>
      val filename = s"${name}_$size.csv"
      val file = dataFile(filename)
      writeToFile(file, pw => {
        val west = new InitialPlacementNone
        val east = initialPlacement
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

      val stv: SensToVec = new FlagDirectionSensToVector()

      var cnt = 0

      def apply(sens: Sensors, player: Player): Unit = {

        import PlacementUtil._

        val vp: VsocPlayer = player.asInstanceOf[VsocPlayer]
        if (vp.isTeamEast) {
          // Creating test data for 'east' players would require some extra transformation
          // of x, y and direction
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

      def getChild: Optional[Behaviour] = Optional.empty()

      def shouldBeApplied(sens: Sensors): Boolean = true
    }
    new BehaviourController(behav)
  }

  def format(cnt: Int, pos: Vec2D, dir: Double, features: Array[Double]): String = {

    val cntStr = Formatter.formatWide(cnt)
    val xStr = Formatter.formatWide(pos.getX)
    val yStr = Formatter.formatWide(pos.getY)
    val dirStr = Formatter.formatWide(dir)
    val featuresStr = Formatter.formatLimitatedWide(features)
    s"$cntStr$sepaStr$xStr$sepaStr$yStr$sepaStr$dirStr$sepaStr$featuresStr"
  }

}

object Placement {

  import PlacementUtil._

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

  def placeControllerStraightFromCenter(dash: Int): (Player, Int) => Unit = {
    case (player, _) =>
      player.dash(dash)
  }

}

import vsoc.server.InitialPlacement
import vsoc.server.InitialPlacement._

class InitialPlacementRandomPos(val numberOfPlayers: Int) extends InitialPlacement {

  import PlacementUtil._

  def placementValuesWest(num: Int): Values = {
    val x = ran(-55, 55)
    val y = ran(-35, 35)
    val dir = ran(0, 360)
    new Values(x, y, degToRad(dir))
  }

}

class InitialPlacementFullControl(x: Double, y: Double, dir: Double) extends InitialPlacement {

  def numberOfPlayers = 1

  def placementValuesWest(num: Int): Values = {
    new Values(x, y, dir)
  }

}

object PlacementUtil {

  private val rand = new Random
  private val _radToDeg = 180.0 / math.Pi
  private val _degToRad = math.Pi / 180.0

  def ran(from: Int, to: Int): Int = {
    require(from < to)
    val w = to - from
    from + rand.nextInt(w)
  }

  def radToDeg(rad: Double): Double = {
    val deg = rad * _radToDeg
    val m360 = deg % 360.0
    if (m360 < -180) m360 + 360
    else if (m360 > 180) m360 - 360
    else m360
  }

  def degToRad(deg: Double): Double = {
    deg * _degToRad
  }


}