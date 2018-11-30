package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB04
import vsoc.ga.trainga.analyse.old.dia.CreateDiasData02

object AnalyseB04 extends App {

  val workDir = UtilPath.workDir
  CreateAllResourcesB04.create(workDir)
  val iterWorkDir: Path = workDir.resolve("iter05/work")
  CreateDiasData02.create(DiaFactoriesB04)(iterWorkDir)

}
