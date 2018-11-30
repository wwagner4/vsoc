package vsoc.ga.trainga.analyse.iterations.iter06

import vsoc.ga.common.UtilPath
import vsoc.ga.trainga.analyse.old.dia.CreateDiasData02

object AnalyseB05w extends App {

  val workDir = UtilPath.workDir
  CreateDiasData02.create(DiaFactoriesB05w)(workDir)

}
