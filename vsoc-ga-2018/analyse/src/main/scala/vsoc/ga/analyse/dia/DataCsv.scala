package vsoc.ga.analyse.dia

import java.io.PrintWriter
import java.nio.file.{Files, Path, Paths}

import vsoc.ga.common.UtilPath
import vsoc.ga.common.config.{Config, ConfigHelper, ConfigTrainGa}

abstract class DataCsv[T] {

  protected def sepa = "\t"

  protected def colWidth = 13

  def csvReader: CsvReader[T]

  def fmtStr(fmtDef: Seq[Fmt]): String = {
    fmtDef.map(_.toString).mkString(sepa)
  }

  def defaultHeaders(headers: Seq[String]): String = {
    headers.map(_.toString).mkString(sepa)
  }

  trait Formatter[U] {

    def header: PrintWriter => Unit

    def data: (T, PrintWriter) => Unit
  }

  trait Fmt
  case object F extends Fmt {
    override def toString: String = s"%$colWidth.2f"
  }
  case object S extends Fmt {
    override def toString: String = s"%${colWidth}s"
  }

  case object D extends Fmt {
    override def toString: String = s"%${colWidth}d"
  }

  protected val _workDir: Path = UtilPath.workDir

  def createCsvConfig(
                       cfg: Config,
                     ): Unit = {
    val pw = createPrintWriter(cfg)
    try {
      createCsv(cfg.trainings, cfg.id, pw)
    } finally {
      pw.close()
    }
  }

  def createPrintWriter(config: Config): PrintWriter = new PrintWriter(System.out)

  def formatter: Formatter[T]

  private def createCsv(
                         trainGas: Seq[ConfigTrainGa],
                         diaId: String,
                         printWriter: PrintWriter): Unit = {
    formatter.header(printWriter)
    for (ConfigTrainGa(id, nr) <- trainGas) {
      println(s"writing $id $nr")
      def prjDir = _workDir.resolve(Paths.get(id, nr))
      def filePath = _workDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))
      println(s"reading $filePath")
      require(Files.exists(filePath))
      val raw: Seq[T] = csvReader.read(filePath)
      val n = raw.size
      println(s"found $n lines")
      raw.foreach(l => formatter.data(l, printWriter))
    }


  }
}
