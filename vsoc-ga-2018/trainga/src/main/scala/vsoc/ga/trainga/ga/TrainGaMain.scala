package vsoc.ga.trainga.ga

import java.nio.file.{Files, Path}

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

object TrainGaMain extends App {


  if (args.length != 1) {
    println(usage)
  } else {
    val id = args(0)
    try {
      val cfg = UtilReflection.call(Configs, id, classOf[Config])
      val wdBase = cfg.workDirBase
      configureLogfile(wdBase)
      for(c <- cfg.trainings.par) {
        TrainGaRunner.run(wdBase, c)
      }
    } catch {
      case e: ScalaReflectionException =>
        println(s"Invalid configuration '$id'")
        println(usage)
    }
  }

  private def usage =
    """usage ...TrainGaMain <configId>
      | - id: Configuration ID. One of the method defined in Configurations. E.g. 'walKicks001', 'bobKicks001', ...
    """.stripMargin

  private def configureLogfile(workDirBase: Path): Unit = {
    val p = workDirBase.resolve("logs")
    Files.createDirectories(p)
    val f = p.resolve(s"vsoc-ga-2018.log")
    System.setProperty("logfile.name", f.toString)
    println(s"Writing log to $f")
  }


}
