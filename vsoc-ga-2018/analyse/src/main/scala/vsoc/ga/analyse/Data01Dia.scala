package vsoc.ga.analyse

import vsoc.ga.common.data.Data01

class Data01Dia extends DataDia[Data01](new CsvReaderData01) {

  override def origin(id: String, nr: String): Data01 = Data01(id, nr)

  override def x(data: Data01): Double = data.iterations

  override def y(data: Data01): Double = data.score
}

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
