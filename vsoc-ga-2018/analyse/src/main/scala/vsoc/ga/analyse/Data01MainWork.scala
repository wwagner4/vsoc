package vsoc.ga.analyse

import entelijan.viz.Viz
import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val configs = Seq(
    Configs.work001,
    Configs.work002,
    Configs.work003,
  )

  configs.foreach{c =>
    Data01Dia.run(
      cfg = c,
      filterFactor = 10,
      minIter = 0,
      yRange = Some(Viz.Range(None, Some(100)))
    )
  }

}
