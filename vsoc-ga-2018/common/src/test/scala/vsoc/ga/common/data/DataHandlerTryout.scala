package vsoc.ga.common.data

import java.nio.file.Paths

object DataHandlerTryout extends App {


  val data1 = Seq(
    Seq(("a", 3.5), ("name", "Hugo"), ("b", 110.5)),
    Seq(("a", 3.53), ("name", "Bertl"), ("b", 11000.5)),
    Seq(("a", 4.523), ("name", "Anna"), ("b", 23.0)),
    Seq(("a", 5.3), ("name", "Guthelf"), ("b", 2)),
    Seq(("a", 6.4), ("name", "Grommit"), ("b", -10)),
  )

  val data2 = Seq(
    Seq(("a", 1000), ("name", "Wolfi"), ("b", -1000)),
  )

  val f = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018", "test", "d1.csv")

  val d = new DataHandler(f)


  for (l <- data1) {
    d.writeLine(l)
  }
  for (l <- data2) {
    d.writeLine(l)
  }
}
