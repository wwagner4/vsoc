package playerpos

import scala.collection.JavaConverters._

import vsoc.server.ServerUtil

object PlayerposMain extends App {

  import common.Util._


  val sizes = List(1000, 5000, 10000, 50000)

  sizes.foreach{size =>
    val filename = s"pos_$size.txt"
    val file = dataFile(filename)
    writeToFile(file, pw => {
      val srv = ServerUtil.current().createServer(10, 10)
      srv.getPlayers.asScala.foreach { p =>
        val ctrl = Playerpos.createController(Some(pw))
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

