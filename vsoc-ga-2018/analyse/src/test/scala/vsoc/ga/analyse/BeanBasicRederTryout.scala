package vsoc.ga.analyse

import java.nio.file.Paths

object BeanBasicRederTryout extends App {

  val path = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018", "trainGaKicks01", "004", "trainGaKicks01-004-data.csv")

  val beans = BeanBasicReader.read(path)

  beans.foreach(b => println(b))
}
// /Users/wwagner4/work/work-vsoc-ga-2018/trainGaKicks01/004/trainGaKicks01-004-data.csv
// /Users/wwagner4/work/work-vsoc-ga-2018/trainGaKicks01/004