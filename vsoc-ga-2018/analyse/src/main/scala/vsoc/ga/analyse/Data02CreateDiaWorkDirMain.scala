package vsoc.ga.analyse

import entelijan.viz.Viz

object Data02CreateDiaWorkDirMain extends App {

  // Add Multiple training configurations
  val _incl = Seq(
    "trainGaB",
  )


  new Data02Dia().createDiaWorkDir(
    id="002",
    xRange = Some(Viz.Range(Some(0), Some(800))),
    yRange = Some(Viz.Range(Some(0), Some(140000))),
    includes = Some(_incl),
  )

}
