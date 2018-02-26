package vsoc.ga.analyse

sealed trait DataGa

case class DataGa_One(id: String, nr: String) extends DataGa
case class DataGa_Multi(id: String, title: String, datas: Seq[(String, String)]) extends DataGa

object BeanBasicMain extends App {

  // e.g. ("trainGaKicks01", "w002"), ("trainGaKicks01", "w001"), ("trainGa01", "w001"), ...

  val dataGa001 = DataGa_Multi("multi000", "Train Kicks 01", Seq(
    ("trainGaKicks01", "w001"),
    ("trainGaKicks01", "w002"),
  ))

  val dataGa002 = DataGa_One("trainGaKicks01", "w001")
  val dataGa003 = DataGa_One("trainGaKicks01", "w002")
  val dataGa004 = DataGa_One("trainGa01", "w001")

  val dataGa005 = DataGa_Multi("trainGaKicks01_001", "trainGaKicks01", Seq(
    ("trainGaKicks01", "004"),
    ("trainGaKicks01", "006"),
  ))

  BeanBasicDia.run(dataGa005)

}
