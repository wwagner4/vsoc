package vsoc.ga.common.commandline

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

trait WithConfigRunner {


  def runWithConfig(_args: Array[String], f: Config => Unit, clazz: String): Unit = {
    if (_args.length != 1) {
      println(usage(clazz))
    } else {
      val id = _args(0)
      try {
        val cfg = UtilReflection.call(Configs, id, classOf[Config])
        f(cfg)
      } catch {
        case e: ScalaReflectionException =>
          println(s"Invalid configuration '$id' ${e.getMessage}")
          println(usage(clazz))
      }
    }
  }

  private def usage(clazz: String): String = {
    s"""usage ...$clazz <configId>
       | - id: Configuration ID. One of the method defined in Configurations. E.g. 'walKicks001', 'bobKicks001', ...
    """.stripMargin
  }


}
