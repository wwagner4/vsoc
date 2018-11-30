package vsoc.ga.trainga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.analyse.old.dia.CreateDiasData02
import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB03

object AnalyseB03 extends App {

  val baseDir = UtilPath.workDir
  val iterDir: Path = CreateAllResourcesB03.create(baseDir)
  println(s"created resources $iterDir")
  implicit val iterWorkDir: Path = iterDir.resolve("work")

  CreateDiasData02.create(DiaFactoriesB03)

}
