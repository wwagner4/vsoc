package vsoc.ga.trainga.ga

import java.nio.file.{Files, Paths}

object TrainGaWallaceMain extends App {

  val host = "wallace"
  val trainGaId = "trainGaKicks01"

  val runs = Seq(
    (trainGaId, s"${host}001"),
    (trainGaId, s"${host}002"),
    (trainGaId, s"${host}003"),
  )

  configureLogfile()

  for ((id, nr) <- runs.par) {
    TrainGaRunner.run(trainGaId = id, trainGaNr = nr)
  }

  private def configureLogfile(): Unit = {
    val p = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018")
    Files.createDirectories(p)
    val f = p.resolve(s"vsoc-ga-2018-$host.log")
    System.setProperty("logfile.name", f.toString)
    println(s"Writing log to $f")
  }


}
