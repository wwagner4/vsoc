package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.trainga.analyse.common.dia.CreateDiasData02
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseB04 extends App {

  val workDir = ConfigHelper.workDir
  CreateAllResourcesB04.create(workDir)
  val iterWorkDir: Path = workDir.resolve("iter05/work")
  CreateDiasData02.create(DiaFactoriesB04)(iterWorkDir)

}
