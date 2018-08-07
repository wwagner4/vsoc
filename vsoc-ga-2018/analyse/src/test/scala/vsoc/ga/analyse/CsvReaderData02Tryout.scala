package vsoc.ga.analyse

object CsvReaderData02Tryout extends App {

  val  reader = new vsoc.ga.analyse.CsvReaderData02
  for((line, nr) <- reader.read("trainGaB01").zipWithIndex) {
    println(s"$nr - $line")
  }

}
