package vsoc.ga.analyse.server

import java.nio.file._

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

object Server extends App {

  require(args.length == 2, "Three parameters required")

  val httpDir = args(0)
  val configsList = args(1)

  val httpPath = Paths.get(httpDir)
  require(httpPath.isAbsolute, s"'$httpPath' must be an absolute path")

  val configs: Seq[Config] = configsList.split(",").toSeq.map(getConfig)
  require(configs.nonEmpty, "at least one configuration must be defined")

  ServerImpl.start(httpPath, configs)

  def getConfig(name: String): Config = UtilReflection.call(Configs, name, classOf[Config])

}

