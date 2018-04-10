package vsoc.ga.analyse.server

import java.nio.file._

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

object Server extends App {

  require(args.length == 3, "Three parameters required")

  val workDir = args(0)
  val httpDir = args(1)
  val configsList = args(2)

  val workPath = Paths.get(workDir)
  require(workPath.isAbsolute, s"'$workPath' must be an absolute path")

  val httpPath = Paths.get(httpDir)
  require(httpPath.isAbsolute, s"'$httpPath' must be an absolute path")

  val configs: Seq[Config] = configsList.split(",").toSeq.map(getConfig)
  require(configs.nonEmpty, "at least one configuration must be defined")

  ServerImpl.start(workPath, httpPath, configs)

  def getConfig(name: String): Config = UtilReflection.call(Configs, name, classOf[Config])

}

