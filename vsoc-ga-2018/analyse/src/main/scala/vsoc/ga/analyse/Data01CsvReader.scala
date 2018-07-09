package vsoc.ga.analyse

import vsoc.ga.common.data.Data01

object Data01CsvReader extends AbstractCsvReader[Data01] {



  def toBean(line: String): Data01 = {
    val a = line.split(";")
    Data01(
      a(0),
      a(1),
      toInt(a(2)),
      toDouble(a(3))
    )
  }



}
