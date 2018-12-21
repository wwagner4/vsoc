package vsoc.ga.matches.nn.analyse

import java.nio.file.{Files, Path, Paths}

import vsoc.ga.matches.behav.OutputMappers
import vsoc.ga.matches.nn.NeuralNets

object InputDataModelMain extends App {

  val wd = workDir

  val num = 5000

  val handlers = Seq(
    InputDataHandlers.boxPlots("team01om02varL", NeuralNets.team01, OutputMappers.om02varL, Some(100), wd),
    InputDataHandlers.boxPlots("team02om02varL", NeuralNets.team02, OutputMappers.om02varL, Some(100), wd),
  )

  for (handler <- handlers) {
    new InputDataModel().run(handler, num)
  }

  def workDir: Path ={
    val wds = System.getProperty("user.home")
    val wdr = Paths.get("work/work-vsoc-ga-2018/input-data")
    val wd = Paths.get(wds).resolve(wdr)
    if (!Files.exists(wd)) Files.createDirectories(wd)
    wd
  }

}



