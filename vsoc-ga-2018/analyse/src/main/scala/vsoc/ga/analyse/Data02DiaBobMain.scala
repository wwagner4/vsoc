package vsoc.ga.analyse

object Data02DiaBobMain extends App {

  new Data02Dia().createDiaTrainGa(
    trainGa = "trainGaB01",
    diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP),
  )

}

