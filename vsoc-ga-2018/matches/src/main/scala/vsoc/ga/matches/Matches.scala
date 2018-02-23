package vsoc.ga.matches

import vsoc.server.ServerUtil
import vsoc.server.gui.{FieldPanel, SimulationChangeListener}
import vsoc.server.initial.InitialPlacementLineup

import scala.collection.JavaConverters._

object Matches {

  private val su = ServerUtil.current()

  def of(east: Team, west: Team): Match = {

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

    var steps = 0

    new Match {
      override def state: MatchResult = {
        MatchResults.of(steps, server.getPlayersEast.asScala, server.getPlayersWest.asScala)
      }

      override def takeStep(): Unit = {
        server.takeStep()
        steps += 1
      }

      override def addSimListener(listener: SimulationChangeListener): Unit = {
        listener match {
          case p: FieldPanel => p.setSim(server)
          case _ => // For others do nothing
        }
        server.addListener(listener)
      }

      override def teamWestName: String = west.name

      override def teamEastName: String = east.name
    }
  }

}
