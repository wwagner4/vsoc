package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB04
import vsoc.ga.trainga.ga.TrainGas
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB04 extends App {

  val workDir = UtilPath.workDir
  val iterBaseDir = CreateAllResourcesB04.create(workDir)
  implicit val iterWorkDir: Path = iterBaseDir.resolve("work")

  val popNr = "bob001"
  val tga = TrainGas.trainGaB04

  val tgaStr = tga.id

  println(s"name = '${tgaStr}_$popNr'")

  GuiPopulationRunner.run(tga, popNr, None)

}
