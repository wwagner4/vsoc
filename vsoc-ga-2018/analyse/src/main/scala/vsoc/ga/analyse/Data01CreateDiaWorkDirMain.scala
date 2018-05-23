package vsoc.ga.analyse

import entelijan.viz.Viz

object Data01CreateDiaWorkDirMain extends App {

  val _incl = Seq(
    "trainGa04G0",
    "trainGa04K0",
    "trainGa04M0",
    "trainGa04M0om02",
    "trainGa04M0om02varL",
    "trainGa05fitFac01",
    "trainGa05fitFac02",
    "trainGa05fitFac03",
    //"trainGa05fitFac03a",
  )


  Data01Dia.createDiaWorkDir(
    id="002",
    xRange = Some(Viz.Range(Some(0), Some(800))),
    yRange = Some(Viz.Range(Some(0), Some(140000))),
    includes = Some(_incl),
  )

}
