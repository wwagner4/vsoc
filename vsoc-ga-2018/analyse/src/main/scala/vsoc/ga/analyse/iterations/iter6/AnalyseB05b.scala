package vsoc.ga.analyse.iterations.iter6

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath

object AnalyseB05b extends App {

  val workDir = UtilPath.workDir
  CreateDiasData02.create(DiaFactoriesB05b)(workDir)

}
