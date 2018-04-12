package vsoc.ga.analyse

import java.nio.file.Paths

import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val configs = Seq(
    Configs.work007,
  )

  val _wd: String = "C:\\ta30\\entw1\\work"

  configs.foreach{c =>
    Data01Dia.run(
      cfg = c,
      workDir = Paths.get(_wd),
      //filterFactor = 10,
      //yRange = Some(Viz.Range(None, Some(100)))
    )
  }

}
