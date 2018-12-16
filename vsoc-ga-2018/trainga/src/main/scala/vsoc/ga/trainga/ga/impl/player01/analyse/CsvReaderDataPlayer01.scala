package vsoc.ga.trainga.ga.impl.player01.analyse

import vsoc.ga.trainga.analyse.old.dia.CsvReader
import vsoc.ga.trainga.ga.impl.player01.DataPlayer01


class CsvReaderDataPlayer01 extends CsvReader[DataPlayer01] {

  override def toBean(line: String): DataPlayer01 = {
    val sl = line.split(";")
    DataPlayer01(
      iterations = toInt(sl(0)),
      kicks = toDouble(sl(1)),
      goals = toDouble(sl(2)),
    )
  }

}
