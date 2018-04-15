package vsoc.ga.analyse

import entelijan.viz.Viz

object Data01CreateDiaWorkDirMain extends App {

  Data01Dia.createDiaWorkDir(
    xRange = Some(Viz.Range(Some(0), Some(500))),
    yRange = Some(Viz.Range(Some(0), Some(500)))
  )

}
