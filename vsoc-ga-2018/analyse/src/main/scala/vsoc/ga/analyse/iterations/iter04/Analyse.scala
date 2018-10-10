package vsoc.ga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.analyse.dia.CreateDiasData02
import vsoc.ga.common.UtilPath

object Analyse extends App {

  val baseDir = UtilPath.workDir
  implicit val iterDir: Path = CreateAllResources.create(baseDir)
  println(s"created resources $iterDir")

  CreateDiasData02.create(DiaFactoriesB03)

}
