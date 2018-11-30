package vsoc.ga.trainga.analyse.old.dia

import vsoc.ga.trainga.ga.Data01

class CsvReaderData01 extends CsvReader[Data01] {
  override def toBean(line: String): Data01 = {
    val a = line.split(";")
    Data01(
      a(0),
      a(1),
      toInt(a(2)),
      toDouble(a(3))
    )
  }
}

