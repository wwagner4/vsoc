package vsoc.ga.analyse.server

import java.nio.file._
import java.util.concurrent.TimeUnit

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

object Server extends App {

  require(args.length == 3, "Three parameters required")

  val httpDir = args(0)
  val trainGa = args(1)
  val seconds = args(2).toInt

  val httpPath = Paths.get(httpDir)
  require(httpPath.isAbsolute, s"'$httpPath' must be an absolute path")

  ServerImpl.start(httpPath, trainGa, Period(seconds, TimeUnit.SECONDS))

  def getConfig(name: String): Config = UtilReflection.call(Configs, name, classOf[Config])

}

