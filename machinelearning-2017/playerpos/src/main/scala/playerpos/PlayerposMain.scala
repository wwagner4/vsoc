package playerpos

import scala.collection.JavaConverters._

import vsoc.server.ServerUtil

object PlayerposMain extends App {

  import common.Util._

  val file = dataFile("pos04.txt")
  writeToFile(file, pw => {
    val srv = ServerUtil.current().createServer(10, 10)
    srv.getPlayers.asScala.foreach { p =>
      val ctrl = Playerpos.createController(Some(pw))
      p.setController(ctrl)
    }
    for (_ <- 1 to 1000) {
      srv.takeStep()
    }
  })

  println(s"wrote to $file")
}

