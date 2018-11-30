package vsoc.ga.analyse.old.dia

import vsoc.ga.common.data.Data02

class CsvReaderData02 extends CsvReader[Data02] {
  override def toBean(line: String): Data02 = {
    val a = line.split(";")
    Data02(
      a(0),
      a(1),
      toInt(a(2)),
      toDouble(a(3)),
      toDouble(a(4)),
      toDouble(a(5)),
      toDouble(a(6)),
      toDouble(a(7)),
      toDouble(a(8)),
      toDouble(a(9)),
      toDouble(a(10)),
      toDouble(a(11)),
      toDouble(a(12)),
      toDouble(a(13)),
      toDouble(a(14)),
      toDouble(a(15)),
      toDouble(a(16))
    )
  }
}

