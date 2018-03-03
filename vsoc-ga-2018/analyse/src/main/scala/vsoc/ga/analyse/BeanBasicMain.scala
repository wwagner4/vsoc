package vsoc.ga.analyse

sealed trait DataGa

case class DataGa_One(id: String, nr: String) extends DataGa
case class DataGa_Multi(id: String, title: String, datas: Seq[(String, String)]) extends DataGa

object BeanBasicMain extends App {

  // e.g. ("trainGaKicks01", "w002"), ("trainGaKicks01", "w001"), ("trainGa01", "w001"), ...

  val dataGa_w001 = DataGa_Multi("multi_0001", "Train Kicks 01", Seq(
    ("trainGaKicks01", "w001"),
    ("trainGaKicks01", "w002"),
  ))

  val dataGa_w002 = DataGa_Multi("multi_w002", "Train All 01", Seq(
    ("trainGa01", "w001"),
    ("trainGa01", "w002"),
  ))

  val dataGa_002 = DataGa_One("trainGaKicks01", "w001")
  val dataGa_003 = DataGa_One("trainGaKicks01", "w002")
  val dataGa_004 = DataGa_One("trainGa01", "w001")

  val dataGa_005 = DataGa_Multi("trainGaKicks01_001", "trainGaKicks01", Seq(
    ("trainGaKicks01", "004"),
    ("trainGaKicks01", "006"),
    ("trainGaKicks01", "007"),
  ))

  BeanBasicDia.run(dataGa_005)

}
