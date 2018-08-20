package vsoc.ga.analyse

import java.nio.file.{Path, Paths}

import vsoc.ga.common.config.ConfigHelper

object DataDiaMain extends App {

  def trainGaB01(): Unit = {
    val _trainGa = "trainGaB01"
    val rel = Paths.get("viz_img", _trainGa)
    val diaDir = ConfigHelper.workDir.resolve(rel)

    val factories = Seq(
      DiaFactories.scoreGroupedByPopulation,
      DiaFactories.scoreCompositionB01,
      DiaFactories.kicksB01,
    )

    for (f <- factories) {
      new Data02Dia().createDiaTrainGa(
        trainGa =_trainGa,
        diaFactory = f,
        diaDir = Some(diaDir)
      )
    }
  }


  def trainGaB02(): Unit = {
    val _trainGa = "trainGaB02"
    val rel = Paths.get("viz_img", _trainGa)
    val diaDir = ConfigHelper.workDir.resolve(rel)

    val factories = Seq(
      DiaFactories.scoreGroupedByPopulation,
      DiaFactories.scoreCompositionB02,
      DiaFactories.kicksB02,
      DiaFactories.goalsB02,
      DiaFactories.kicksToKickOutB02,
    )

    for (f <- factories) {
      new Data02Dia().createDiaTrainGa(
        trainGa = _trainGa,
        diaFactory = f,
        diaDir = Some(diaDir)
      )
    }
  }

  trainGaB02()

}

