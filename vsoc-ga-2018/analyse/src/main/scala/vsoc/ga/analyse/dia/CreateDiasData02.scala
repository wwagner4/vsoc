package vsoc.ga.analyse.dia

import java.nio.file.{Path, Paths}

import vsoc.ga.common.data.Data02

object CreateDiasData02 {

  def create(diaFactories: DiaFactories[Data02])(implicit iterDir: Path): Unit = {
    val diaDir = iterDir.resolve("dias")
    val workDir = iterDir.resolve("work")

    for (f <- diaFactories.diaFactories) {
      new Data02Dia().createDiaTrainGa(
        trainGa = diaFactories.trainGaId,
        diaFactory = f,
        workDir = workDir,
        diaDir = Some(diaDir)
      )
    }
  }

}
