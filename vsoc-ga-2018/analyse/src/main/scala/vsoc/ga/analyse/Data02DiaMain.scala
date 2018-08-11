package vsoc.ga.analyse

object Data02DiaMain extends App {

  val factories = Seq(
    DiaFactories.scoreGroupedByPopulation,
    DiaFactories.scoreComposition,
    DiaFactories.kicks,
  )

  for (f <- factories) {
    new Data02Dia().createDiaTrainGa(
      trainGa = "trainGaB02",
      diaFactory = f
    )
  }

}

