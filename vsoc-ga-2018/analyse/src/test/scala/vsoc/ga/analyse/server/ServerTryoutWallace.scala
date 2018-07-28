package vsoc.ga.analyse.server

import java.nio.file.Paths
import java.util.concurrent.TimeUnit

import vsoc.ga.common.config.Configs

object ServerTryoutWallace extends App {

  //Server.main(Array("/Users/wwagner4/work/work-vsoc-ga-2018/http", "bob006"))

  val httpPath = Paths.get("/Users/wwagner4/work/work-vsoc-ga-2018/http")
  val configs = Seq(
    Configs.bobB01,
  )

  ServerImpl.start(httpPath, configs, Period(5, TimeUnit.SECONDS))
}
