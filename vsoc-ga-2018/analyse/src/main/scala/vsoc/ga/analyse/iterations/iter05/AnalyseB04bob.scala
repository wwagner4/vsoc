package vsoc.ga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB03

object AnalyseB04bob extends App {

  val baseDir: Path = UtilPath.workDir
  CreateDiasData02.create(DiaFactoriesB04bob)(baseDir)

}
