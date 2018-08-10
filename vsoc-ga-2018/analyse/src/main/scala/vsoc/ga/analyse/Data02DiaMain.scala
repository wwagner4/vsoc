package vsoc.ga.analyse

object Data02DiaMain extends App {

  val factoriesB01 = Seq(
    DiaFactories.scoreGroupedByPopulation,
    DiaFactories.scoreCompositionB01,
    DiaFactories.kicksB01,
  )

  val factoriesB02 = Seq(
    DiaFactories.scoreGroupedByPopulation,
    DiaFactories.scoreCompositionB02,
    DiaFactories.kicksB02,
  )

  for (f <- factoriesB02) {
    new Data02Dia().createDiaTrainGa(
      trainGa = "trainGaB02",
      diaFactory = f,
    )
  }

}

