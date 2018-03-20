package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val cfg = Configs.work003
  Data01Dia.run(
    cfg = cfg,
    filterFactor = 10,
    //minIter = 0,
    //yRange = Some(Viz.Range(None, Some(40)))
    )

}
