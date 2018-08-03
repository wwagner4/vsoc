package vsoc.ga.analyse

import vsoc.ga.common.data.Data02

class Data02Csv extends DataCsv[Data02](new CsvReaderData02){

  override def writeLine(line: Data02): Unit = {
    println("%10s %10s %10.1f" format(
      line.trainGaId,
      line.trainGaNr,
      line.score
    ))
  }

}
