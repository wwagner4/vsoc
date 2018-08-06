package vsoc.ga.analyse.server

import java.nio.file.Paths
import java.util.concurrent.TimeUnit

object ServerTryoutWallace extends App {

  val httpPath = Paths.get("/Users/wwagner4/work/work-vsoc-ga-2018/http")
  val trainGa = "trainGaB01"

  ServerImpl.start(httpPath, trainGa, Period(5, TimeUnit.SECONDS))
}
