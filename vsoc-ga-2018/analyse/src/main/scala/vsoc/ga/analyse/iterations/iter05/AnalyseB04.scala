package vsoc.ga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath

object AnalyseB04 extends App {

  val baseDir: Path = UtilPath.workDir.resolve("iter05/work")
  CreateDiasData02.create(DiaFactoriesB04)(baseDir)

}
