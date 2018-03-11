package vsoc.ga.common.commandline

import java.nio.file.{Path, Paths}

import org.slf4j.LoggerFactory

trait WithPathRunner {

  private val log = LoggerFactory.getLogger(classOf[WithPathRunner])

  def runWithPath(args: Array[String], f: Path => Unit, clazz: String): Unit = {
    if (args.length != 1) {
      println(usage(clazz))
    } else {
      try {
        f(Paths.get(args(0)))
      } catch {
        case e: Exception =>
          log.error(s"Error thinning ${args(0)}. ${e.getMessage}", e)
          println(s"${e.getMessage}")
          println(usage(clazz))
      }
    }
  }

  private def usage(clazz: String): String = {
    s"""usage ...$clazz <path>
       | - path: absolute path in the filesystem. e.g. /home/user/work, /tmp, ...
    """.stripMargin
  }


}
