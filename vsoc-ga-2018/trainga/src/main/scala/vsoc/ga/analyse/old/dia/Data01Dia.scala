package vsoc.ga.analyse.old.dia

import vsoc.ga.trainga.ga.Data01

class Data01Dia extends DataDia[Data01] {

  override def csvReader: CsvReader[Data01] = new CsvReaderData01

}

