package vsoc.ga.trainga.analyse.iterations.iter06

import vsoc.ga.trainga.analyse.old.dia.CreateDiasData02
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseB05w extends App {

  val workDir = ConfigHelper.workDir
  CreateDiasData02.create(DiaFactoriesB05w)(workDir)

}
