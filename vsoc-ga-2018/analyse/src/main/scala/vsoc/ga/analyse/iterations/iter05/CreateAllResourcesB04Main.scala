package vsoc.ga.analyse.iterations.iter05

import java.nio.file.{Path, Paths}

import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB04

object CreateAllResourcesB04Main extends App {

  val baseDir = UtilPath.workDir
  CreateAllResourcesB04.create(baseDir)

}
