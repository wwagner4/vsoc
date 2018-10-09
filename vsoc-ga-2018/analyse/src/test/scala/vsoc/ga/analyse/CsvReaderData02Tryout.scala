package vsoc.ga.analyse

import vsoc.ga.analyse.dia.CsvReaderData02

object CsvReaderData02Tryout extends App {

  val  reader = new CsvReaderData02
  for((line, nr) <- reader.read("trainGaB01").zipWithIndex) {
    println(s"$nr - $line")
  }

}
