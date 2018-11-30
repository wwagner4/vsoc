package vsoc.ga.trainga.analyse.iterations.iter06

import vsoc.ga.analyse.old.dia.CreateDiasData02
import vsoc.ga.common.UtilPath

object AnalyseB05b extends App {

  val workDir = UtilPath.workDir
  CreateDiasData02.create(DiaFactoriesB05b)(workDir)

}
