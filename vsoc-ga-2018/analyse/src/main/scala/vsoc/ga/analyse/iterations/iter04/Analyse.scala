package vsoc.ga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB03

object Analyse extends App {

  val baseDir = UtilPath.workDir
  implicit val iterDir: Path = CreateAllResourcesB03.create(baseDir)
  println(s"created resources $iterDir")

  CreateDiasData02.create(DiaFactoriesB03)

}
