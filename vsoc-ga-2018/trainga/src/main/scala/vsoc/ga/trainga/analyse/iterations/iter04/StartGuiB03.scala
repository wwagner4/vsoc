package vsoc.ga.trainga.analyse.iterations.iter04

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.common.analyse.iterations.iter04.CreateAllResourcesB03
import vsoc.ga.trainga.ga.TrainGas
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB03 extends App {

  val baseDir = UtilPath.workDir
  implicit val workDir: Path = CreateAllResourcesB03.create(baseDir).resolve("work")

  val tga = TrainGas.trainGaB03
  val pops = List(
    "bob001",
    "bob002",
    "bob003",
    "bob004",
    "work001",
    "work002",
    "work003",
    "work004",
    "work005",
    "work006",
  )

  val i = 1
  val tgaStr = tga.id
  val popStr = pops(i)

  println(s"name = '${tgaStr}_$popStr'")

  GuiPopulationRunner.run(tga, pops(i), None)

}
