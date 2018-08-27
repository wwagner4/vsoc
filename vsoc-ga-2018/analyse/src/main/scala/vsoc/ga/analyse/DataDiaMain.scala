package vsoc.ga.analyse

import java.nio.file.Paths

import vsoc.ga.common.config.ConfigHelper
import vsoc.ga.common.data.Data02

object DataDiaMain extends App {

  def trainGaData02(diaFactories: DiaFactories[Data02]): Unit = {
    val _trainGa = diaFactories.trainGaId
    val rel = Paths.get("viz_img", _trainGa)
    val diaDir = ConfigHelper.workDir.resolve(rel)

    for (f <- diaFactories.diaFactories) {
      new Data02Dia().createDiaTrainGa(
        trainGa = diaFactories.trainGaId,
        diaFactory = f,
        diaDir = Some(diaDir)
      )
    }
  }

  trainGaData02(DiaFactoriesB03)

}

