package vsoc.ga.trainga.analyse.iterations.iter06

import java.nio.file.Path

import vsoc.ga.trainga.analyse.common.dia.CreateDiasData02
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseB05 extends App {

  val workDir = ConfigHelper.workDir
  CreateAllResourcesB05.create(workDir)
  val iterWorkDir: Path = workDir.resolve("iter06/work")
  CreateDiasData02.create(DiaFactoriesB05w)(iterWorkDir)

}
