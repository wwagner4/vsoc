package vsoc.ga.analyse

import java.nio.file.Paths

import entelijan.viz.Viz
import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val configs = Seq(
    Configs.work001,
    Configs.work002,
    Configs.work003,
  )

  val _wd: String = ???

  configs.foreach{c =>
    Data01Dia.run(
      cfg = c,
      workDir = Paths.get(_wd),
      filterFactor = 10,
      yRange = Some(Viz.Range(None, Some(100)))
    )
  }

}
