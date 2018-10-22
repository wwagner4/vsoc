package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.trainga.ga.TrainGas
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB04w extends App {

  implicit val workDir: Path = UtilPath.workDir

  val tga = TrainGas.trainGaB04
  val popsAll = List(
    "work001",
    "work002",
    "work003",
    "work004",
    "work005",
    "work006",
  )

  val i = 3
  val tgaStr = tga.id
  val popStr = popsAll(i)

  println(s"name = '${tgaStr}_$popStr'")

  GuiPopulationRunner.run(tga, popsAll(i), None)

}
