package vsoc.ga.analyse

import entelijan.viz.Viz
import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val cfg = Configs.work002
  Data01Dia.run(cfg=cfg, filterFactor = 50, minIter = 0,  yRange = Some(Viz.Range(None, Some(40))))

}
