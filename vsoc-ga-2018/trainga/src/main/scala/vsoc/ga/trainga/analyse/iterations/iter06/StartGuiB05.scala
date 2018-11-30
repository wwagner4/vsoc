package vsoc.ga.trainga.analyse.iterations.iter06

import java.nio.file.Path

import vsoc.ga.trainga.config.ConfigHelper
import vsoc.ga.trainga.ga.TrainGas
import vsoc.ga.trainga.gui.GuiPopulationRunner

object StartGuiB05 extends App {

  implicit val workDir: Path = ConfigHelper.workDir

  val popNr = "bob004"
  val tga = TrainGas.trainGaB05

  val tgaStr = tga.id

  println(s"name = '${tgaStr}_$popNr'")

  GuiPopulationRunner.run(tga, popNr, None)

}
