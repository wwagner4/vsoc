package vsoc.ga.trainga.ga

import java.util.concurrent.Executors

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}
import vsoc.ga.trainga.util.UtilTrainGa

object TrainGaMain extends App {


  if (args.length != 1) {
    println(usage)
  } else {
    val id = args(0)
    try {
      val cfg = UtilReflection.call(Configs, id, classOf[Config])
      val wdBase = cfg.workDirBase
      UtilTrainGa.configureLogfile(wdBase)
      val ec = Executors.newFixedThreadPool(cfg.trainings.size)
      for(c <- cfg.trainings) {
        ec.execute(() => TrainGaRunner.run(wdBase, c))
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

}
