package vsoc.ga.analyse.old.dia

import java.nio.file.{Files, Path, Paths}

import vsoc.ga.common.data.Data02

object CreateDiasData02 {

  def create(diaFactories: DiaFactories[Data02])(implicit workDir: Path): Unit = {
    val diaDir = workDir.resolve("dias")

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
