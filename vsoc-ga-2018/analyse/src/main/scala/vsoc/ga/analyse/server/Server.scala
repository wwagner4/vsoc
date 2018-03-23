package vsoc.ga.analyse.server

import java.nio.file.{Path, Paths}
import java.util.concurrent.{Executors, TimeUnit}

import vsoc.ga.analyse.{Data01Dia, DiaConf_SUPRESS_TIMESTAMP}
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

  ServerImpl.start(workPath, httpPath, configs)

  def getConfig(name: String): Config = UtilReflection.call(Configs, name, classOf[Config])

}

object ServerImpl {

  def start(workPath: Path, httpPath: Path, cfgs: Seq[Config]): Unit = {
    val exe = Executors.newScheduledThreadPool(10)
    exe.scheduleAtFixedRate(() => run(), 5, 10, TimeUnit.SECONDS)
    println("started server")

    def run(): Unit = {
      cfgs.foreach{c =>
        Data01Dia.run(c, diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP), diaDir = Some(httpPath))
        println(s"created data for configuration '${c.id}'")
      }
    }
  }

}