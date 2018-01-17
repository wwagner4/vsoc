package vsoc.ga.matches.impl

import vsoc.ga.matches.{Match, MatchResult, Team}
import vsoc.server.ServerUtil
import vsoc.server.initial.InitialPlacementLineup

import scala.collection.JavaConverters._

object Matches {

  val numberOfPlayers = 3

  val su = ServerUtil.current()

  def createMatch(east: Team, west: Team): Match = {
    val placementEast = new InitialPlacementLineup(east.playersCount)
    val placementWest = new InitialPlacementLineup(west.playersCount)
    val server = su.createServer(placementEast, placementWest)

    new Match {

      override def play: MatchResult = {
        val playersEast = server.getPlayersEast.asScala
        for ((p, i) <- playersEast.zipWithIndex) {
          p.setController(east.controller(i))
        }
        val playersWest = server.getPlayersWest.asScala
        for ((p, i) <- playersWest.zipWithIndex) {
          p.setController(east.controller(i))
        }
        ???
      }
    }
  }

}
