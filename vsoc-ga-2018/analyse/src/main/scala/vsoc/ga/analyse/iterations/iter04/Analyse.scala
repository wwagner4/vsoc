package vsoc.ga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.analyse.dia.{CreateDiasData02, DiaFactoriesB01}
import vsoc.ga.common.UtilPath
import vsoc.ga.common.config.ConfigHelper

object Analyse extends App {

  val baseDir = UtilPath.workDir
  implicit val iterDir: Path = CreateAllResources.create(baseDir)
  println(s"created resources $iterDir")

  CreateDiasData02.create(DiaFactoriesB01)

}
