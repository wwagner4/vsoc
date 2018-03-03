package vsoc.ga.trainga.ga

import java.nio.file.{Files, Paths}

object TrainGaMain extends App {

  if (args.length != 2) {
    println(usage)
  } else {
    val id = args(0)
    val nr = args(1)
    configureLogfile(id, nr)
    TrainGaRunner.run(trainGaId = id, trainGaNr = nr)
  }


  private def usage =
    """usage ...TrainGaMain <id> <nr>
      | - id: Training ID. One of the method defined in TrainGas. E.g. 'trainGaKicks01', 'trainGa01', ...
      | - nr: Run ID. A unique number for the run. E.g. 'bob001', 'wallace001', 'wallace002'...
    """.stripMargin

  private def configureLogfile(id: String, nr: String): Unit = {
    val p = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018", id, nr)
    Files.createDirectories(p)
    val f = p.resolve(s"vsoc-ga-2018-$id-$nr.log")
    System.setProperty("logfile.name", f.toString)
    println(s"Writing log to $f")
  }


}
