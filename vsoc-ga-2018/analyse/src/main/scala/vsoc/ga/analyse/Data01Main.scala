package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

sealed trait DataGa

case class DataGa_One(id: String, nr: String) extends DataGa

case class DataGa_Multi(id: String, title: String, datas: Seq[(String, String)]) extends DataGa

object Data01Main extends App {

  val cfg = Configs.bob002
  Data01Dia.run(cfg=cfg, filterFactor = 2, minIter = 950)

}
