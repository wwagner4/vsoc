package vsoc.ga.trainga.ga

import java.nio.file.{Files, Paths}

object TrainGaBobMain extends App {

  val runs = Seq(
    ("trainGaKicks01", "bob001"),
    ("trainGaKicks01", "bob002"),
    ("trainGaKicks01", "bob003"),
  )

  configureLogfile()

  for ((id, nr) <- runs.par) {
    TrainGaRunner.run(trainGaId = id, trainGaNr = nr)
  }

  private def configureLogfile(): Unit = {
    val p = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018")
    Files.createDirectories(p)
    val f = p.resolve(s"vsoc-ga-2018-bob.log")
    System.setProperty("logfile.name", f.toString)
    println(s"Writing log to $f")
  }


}
