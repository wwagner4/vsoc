package vsoc.ga.analyse

import entelijan.viz.Viz

object Data01CreateDiaWorkDirMain extends App {

  Data01Dia.createDiaWorkDir(
    xRange = Some(Viz.Range(Some(0), Some(600))),
    yRange = Some(Viz.Range(Some(0), Some(6000))),
    excludes = Seq("trainGa01_mL", "trainGa01_mM", "trainGa01_mS", "trainGa02", "trainGa03", "trainGaKicks01")
  )

}
