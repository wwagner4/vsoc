package vsoc.ga.analyse

import entelijan.viz.Viz
import vsoc.ga.common.config.ConfigHelper

object Data01CreateDiaWorkDirMain extends App {

  Data01Dia.createDiaWorkDir(
    workDir = ConfigHelper.defaultWorkDir,
    xRange = Some(Viz.Range(Some(0), Some(100))),
    yRange = Some(Viz.Range(Some(0), Some(100)))
  )

}
