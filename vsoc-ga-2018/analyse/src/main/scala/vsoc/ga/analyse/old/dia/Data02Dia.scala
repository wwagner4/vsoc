package vsoc.ga.analyse.old.dia

import vsoc.ga.common.data.Data02

class Data02Dia extends DataDia[Data02] {
  override def csvReader: CsvReader[Data02] = new CsvReaderData02

}

