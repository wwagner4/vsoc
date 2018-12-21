package vsoc.ga.trainga.analyse.common.dia

import vsoc.ga.trainga.ga.impl.team01.Data01

class Data01Dia extends DataDia[Data01] {

  override def csvReader: CsvReader[Data01] = new CsvReaderData01

}

