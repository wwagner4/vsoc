package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

sealed trait DataGa

case class DataGa_One(id: String, nr: String) extends DataGa

case class DataGa_Multi(id: String, title: String, datas: Seq[(String, String)]) extends DataGa

object BeanBasicMain extends App {

  val cfg = Configs.allBobKicks
  BeanBasicDia.run(cfg)

}
