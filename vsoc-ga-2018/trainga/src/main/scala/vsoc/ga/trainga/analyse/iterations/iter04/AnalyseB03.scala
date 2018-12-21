package vsoc.ga.trainga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.trainga.analyse.common.dia.CreateDiasData02
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseB03 extends App {

  val baseDir = ConfigHelper.workDir
  val iterDir: Path = CreateAllResourcesB03.create(baseDir)
  println(s"created resources $iterDir")
  implicit val iterWorkDir: Path = iterDir.resolve("work")

  CreateDiasData02.create(DiaFactoriesB03)

}
