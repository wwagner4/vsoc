package vsoc.ga.analyse

import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

sealed trait DataGa

case class DataGa_One(id: String, nr: String) extends DataGa

case class DataGa_Multi(id: String, title: String, datas: Seq[(String, String)]) extends DataGa

object BeanBasicMain extends App {

  if (args.length != 1) {
    println(usage)
  } else {
    val id = args(0)
    try {
      val cfg = UtilReflection.call(Configs, id, classOf[Config])
      BeanBasicDia.run(cfg)
    } catch {
      case e: ScalaReflectionException =>
        println(s"Invalid configuration '$id'")
        println(usage)
    }
  }

  private def usage =
    """usage ...BeanBasicMain <configId>
      | - id: Configuration ID. One of the method defined in Configurations. E.g. 'walKicks001', 'bobKicks001', ...
    """.stripMargin


}
