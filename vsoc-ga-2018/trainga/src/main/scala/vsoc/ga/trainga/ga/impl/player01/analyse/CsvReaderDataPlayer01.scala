package vsoc.ga.trainga.ga.impl.player01.analyse

import vsoc.ga.trainga.analyse.common.dia.CsvReader
import vsoc.ga.trainga.ga.impl.player01.DataPlayer01


class CsvReaderDataPlayer01 extends CsvReader[DataPlayer01] {

  override def toBean(line: String): DataPlayer01 = {
    val sl = line.split(";")
    DataPlayer01(
      id = sl(0),
      nr = sl(1),
      iterations = toInt(sl(2)),
      kicks = toDouble(sl(3)),
      goals = toDouble(sl(4)),
      score = toDouble(sl(5)),
    )
  }

}
