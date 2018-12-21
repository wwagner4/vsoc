package vsoc.ga.trainga.analyse.old.dia

import vsoc.ga.trainga.ga.impl.team01.Data02

class Data02Dia extends DataDia[Data02] {
  override def csvReader: CsvReader[Data02] = new CsvReaderData02

}

