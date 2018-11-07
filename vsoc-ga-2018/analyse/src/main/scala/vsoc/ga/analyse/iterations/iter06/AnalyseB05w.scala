package vsoc.ga.analyse.iterations.iter06

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath

object AnalyseB05w extends App {

  val workDir = UtilPath.workDir
  CreateDiasData02.create(DiaFactoriesB05w)(workDir)

}
