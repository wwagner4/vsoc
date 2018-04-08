package vsoc.ga.common.commandline

import java.nio.file.{Path, Paths}

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

trait WithConfigRunner {


  def runWithConfig(_args: Array[String], f: (Config, Path) => Unit, clazz: String): Unit = {
    if (_args.length != 2) {
      println(usage(clazz))
    } else {
      val id = _args(0)
      val wdString = _args(1)
      try {
        val cfg = UtilReflection.call(Configs, id, classOf[Config])
        f(cfg, Paths.get(wdString))
      } catch {
        case e: ScalaReflectionException =>
          println(s"Invalid configuration '$id'")
          println(usage(clazz))
      }
    }
  }

  private def usage(clazz: String): String = {
    s"""usage ...$clazz <configId>
       | - id: Configuration ID. One of the method defined in Configurations. E.g. 'walKicks001', 'bobKicks001', ...
       | - workDir: Absolute path to the work dir. E.g. ~/work/, c:/work/, ... The directory is created if ti does not exist.
    """.stripMargin
  }


}
