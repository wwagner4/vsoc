package vsoc.ga.analyse

import java.nio.file.Paths

import vsoc.ga.common.config.Configs

object Data01MainWork extends App {

  val configs = Seq(
    Configs.work008,
  )

  configs.foreach { c =>
    Data01Dia.createDiaConfig(
      cfg = c,
      workDir = Paths.get("C:/ta30/entw1/work")
      //yRange = Some(Viz.Range(None, Some(100)))
    )
  }

}
