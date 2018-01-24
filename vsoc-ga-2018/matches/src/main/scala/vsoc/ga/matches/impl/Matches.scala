package vsoc.ga.matches.impl

import vsoc.ga.matches.{Match, MatchResult, Team}
import vsoc.server.ServerUtil
import vsoc.server.gui.{FieldPanel, SimulationChangeListener}
import vsoc.server.initial.InitialPlacementLineup

import scala.collection.JavaConverters._

object Matches {

  private val su = ServerUtil.current()

  def createMatch(east: Team, west: Team): Match = {

    val placementEast = new InitialPlacementLineup(east.playersCount)
    val placementWest = new InitialPlacementLineup(west.playersCount)
    val server = su.createServer(placementEast, placementWest)

    val playersEast = server.getPlayersEast.asScala
    for ((p, i) <- playersEast.zipWithIndex) {
      p.setController(east.controller(i))
    }
    val playersWest = server.getPlayersWest.asScala
    for ((p, i) <- playersWest.zipWithIndex) {
      p.setController(west.controller(i))
    }


    new Match {
      override def state: MatchResult = {
        new MatchResult {
          override def toString: String = "Result undefined"
        }
      }

      override def takeStep(): Unit = {
          server.takeStep()
      }

      override def addSimListener(listener: SimulationChangeListener): Unit = {
        listener match {
          case p: FieldPanel => p.setSim(server)
        }
        server.addListener(listener)
      }
    }
  }

}
