package vsoc.ga.trainga.analyse.iterations.iter05

import java.nio.file.Path

import vsoc.ga.common.UtilPath
import vsoc.ga.trainga.ga.TrainGas
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB04bob extends App {

  /*
  val baseDir = UtilPath.workDir
  implicit val workDir: Path = CreateAllResourcesB03.create(baseDir).resolve("work")
  */
  implicit val workDir: Path = UtilPath.workDir

  val tga = TrainGas.trainGaB04
  val pops = List(
    "bob001",
    "bob002",
    "bob003",
    "bob004",
  )


  val i =2
  val tgaStr = tga.id
  val popStr = pops(i)

  println(s"name = '${tgaStr}_$popStr'")

  GuiPopulationRunner.run(tga, pops(i), None)

}
