package vsoc.ga.trainga.analyse.iterations.iter06

import vsoc.ga.trainga.analyse.common.dia.CreateDiasData02
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseB05b extends App {

  val workDir = ConfigHelper.workDir
  CreateDiasData02.create(DiaFactoriesB05b)(workDir)

}
