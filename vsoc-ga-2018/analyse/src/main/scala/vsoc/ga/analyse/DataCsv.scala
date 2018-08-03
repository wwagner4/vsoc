package vsoc.ga.analyse

import java.io.PrintWriter
import java.nio.file.Paths

import vsoc.ga.common.config.{Config, ConfigHelper, ConfigTrainGa}

abstract class DataCsv[T](csvReader: CsvReader[T]) {

  private val _workDir = ConfigHelper.workDir

  def createCsvConfig(
                       cfg: Config,
                     ): Unit = {
    val pw = createPrintWriter
    try {
      createCsv(cfg.trainings, cfg.id, pw)
    } finally {
      pw.close()
    }
  }

  def createPrintWriter: PrintWriter = new PrintWriter(System.out)

  def writeLine(line: T): Unit

  private def createCsv(
                         trainGas: Seq[ConfigTrainGa],
                         diaId: String,
                         printWriter: PrintWriter): Unit = {
    for (ConfigTrainGa(id, nr) <- trainGas) {
      println(s"writing $id $nr")
      def prjDir = _workDir.resolve(Paths.get(id, nr))

      def filePath = _workDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val raw: Seq[T] = csvReader.read(filePath)

      val n = raw.size
      println(s"found $n lines")
      raw.foreach(l => writeLine(l))
    }


  }
}
