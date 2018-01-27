package vsoc.ga.matches.impl

import java.util.Optional

import atan.model.Player
import vsoc.behaviour.{Behaviour, DistDirVision, Sensors}

import scala.util.Random

object Behaviours {

  def remainOnField: Behaviour = {
    new Behaviour {

      private val ran = Random

      override def shouldBeApplied(sens: Sensors): Boolean = true

      override def apply(sens: Sensors, player: Player): Unit = {
        if (!sens.sawAnything()) {
          if (ran.nextBoolean()) player.turn(-30)
          else player.turn(30)
        } else {
          player.dash(20)
        }
      }

      override def getChild: Optional[Behaviour] = Optional.empty()

    }
  }

  def randomToggle(child: Behaviour): Behaviour = {
    new Behaviour {

      private val ran = Random

      def ranTurn: Int = {
        ran.nextInt(3) match {
          case 0 => -20
          case 1 => 0
          case 2 => 20
        }
      }

      override def shouldBeApplied(sens: Sensors): Boolean = sens.sawAnything()

      override def apply(sens: Sensors, player: Player): Unit = {
        player.turn(ranTurn)
        player.dash(30)
      }

      override def getChild: Optional[Behaviour] = Optional.of(child)

    }
  }



  def randomHelix(child: Behaviour): Behaviour = {
    new Behaviour {

      private var dir = 0.0
      private val ran = Random

      private val c = 10

      def ranTurn: Double = {
        val i = ran.nextInt(c)
        if (i == 0) {
          if (dir < 6.0) dir += 1.0 else dir -= 3.0
        }
        else if (i == (c - 1)) {
          if (dir > -6.0) dir -= 1.0 else dir += 3.0
        }
        dir
      }

      override def shouldBeApplied(sens: Sensors): Boolean = sens.sawAnything()

      override def apply(sens: Sensors, player: Player): Unit = {
        val t = ranTurn
        //println(s"turn:$t")
        player.turn(t)
        player.dash(50)
      }

      override def getChild: Optional[Behaviour] = Optional.of(child)

    }
  }

  def kickBall(child: Behaviour): Behaviour = {
    new Behaviour {

      private val ran = Random
      private val kickDirVar = 20

      def ballCanBeKiked(sens: Sensors): Boolean = {
        def can (b: DistDirVision): Boolean = math.abs(b.getDirection) < 5.0 && b.getDistance < 2.0
        toScala(sens.getBall).exists(can)
      }

      override def shouldBeApplied(sens: Sensors): Boolean = ballCanBeKiked(sens)

      override def apply(sens: Sensors, player: Player): Unit = {

        def kickDir = ran.nextInt(2 * kickDirVar) - kickDirVar
        def kickPower = ran.nextInt(60) + 10

        player.kick(kickPower, kickDir)
        player.dash(40)
      }

      override def getChild: Optional[Behaviour] = Optional.of(child)

    }
  }

  def towardsTheBall(child: Behaviour): Behaviour = {

    new Behaviour {

      private val ran = Random

      override def shouldBeApplied(sens: Sensors): Boolean = sens.getBall.isPresent && sens.getBall.get().getDistance < 30

      override def apply(sens: Sensors, player: Player): Unit = {
        val dir = sens.getBall.get().getDirection
        player.turn(dir)
        player.dash(ran.nextInt(30))
      }

      override def getChild: Optional[Behaviour] = Optional.of(child)

    }
  }

  def toScala[A](opt: Optional[A]): Option[A] = {
    if (opt.isPresent) Some(opt.get()) else None
  }
}
