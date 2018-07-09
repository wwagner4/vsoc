package vsoc.ga.analyse

import vsoc.ga.common.data.Data02

object Data02CsvReader extends AbstractCsvReader[Data02] {

  def toBean(line: String): Data02 = {
    val a = line.split(";")
    Data02(
      a(0),
      a(1),
      toInt(a(2)),
      toInt(a(3)),
      toInt(a(4)),
      toInt(a(5)),
      toInt(a(6)),
      toInt(a(7)),
      toInt(a(8)),
      toInt(a(9)),
      toInt(a(10)),
      toInt(a(11)),
      toInt(a(12)),
      toDouble(a(13))
    )
  }

}
