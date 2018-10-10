package vsoc.ga.analyse

import vsoc.ga.analyse.dia.CsvReaderData02
import vsoc.ga.common.UtilPath

object CsvReaderData02Tryout extends App {

  val  reader = new CsvReaderData02
  for((line, nr) <- reader.read("trainGaB01")(UtilPath.workDir).zipWithIndex) {
    println(s"$nr - $line")
  }

}
