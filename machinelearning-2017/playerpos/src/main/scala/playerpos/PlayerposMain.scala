package playerpos

import scala.collection.JavaConverters._

import vsoc.server.ServerUtil

object PlayerposMain extends App {

  val srv = ServerUtil.current().createServer(10, 10)
  srv.getPlayers.asScala.foreach { p => p.setController(Playerpos.createController) }

  for (_ <- 1 to 1000) {
    srv.takeStep()
  }
}

