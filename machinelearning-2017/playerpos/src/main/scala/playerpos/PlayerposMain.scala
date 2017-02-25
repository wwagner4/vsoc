package playerpos

import scala.collection.JavaConversions.asScalaBuffer

import vsoc.server.ServerUtil

object Main extends App {

  val srv = ServerUtil.current().createServer(10, 10)
  srv.getPlayers.foreach { p => p.setController(Playerpos.createController) }

  for (i <- (1 to 1000)) {
    srv.takeStep()
  }
}

