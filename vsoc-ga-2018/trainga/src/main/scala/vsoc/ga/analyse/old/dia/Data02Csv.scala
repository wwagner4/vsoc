package vsoc.ga.analyse.old.dia

import java.io.PrintWriter
import java.nio.file.Files

import vsoc.ga.common.config.Config
import vsoc.ga.trainga.ga.Data02

class Data02Csv extends DataCsv[Data02] {

  override def formatter: Formatter[Data02] = FormatterAll

  override def createPrintWriter(cfg: Config): PrintWriter = {
    val outDir = _workDir.resolve("csv")
    if (!Files.exists(outDir)) Files.createDirectories(outDir)
    val id = cfg.id
    val outFile = outDir.resolve(s"$id.csv")
    println(s"writing to $outFile")
    new PrintWriter(Files.newBufferedWriter(outFile))
  }

  case object FormatterScoreOnly extends Formatter[Data02] {

    def header: PrintWriter => Unit = (pw: PrintWriter) => {
      pw.println(defaultHeaders(Seq("id", "nr", "score")))
    }

    def data: (Data02, PrintWriter) => Unit = (line: Data02, pw: PrintWriter) => {
      pw.println(fmtStr(Seq(S, S, F)) format(
        line.trainGaId,
        line.trainGaNr,
        line.score
      ))
    }
  }

  case object FormatterKicks extends Formatter[Data02] {

    def header: PrintWriter => Unit = (pw: PrintWriter) => {
      pw.println(defaultHeaders(Seq("id", "nr", "kicksMax", "kicksMean", "kicksMin", "score")))
    }

    def data: (Data02, PrintWriter) => Unit = (line: Data02, pw: PrintWriter) => {
      pw.println(fmtStr(Seq(S, S, F, F, F, F)) format(
        line.trainGaId,
        line.trainGaNr,
        line.kicksMax,
        line.kicksMean,
        line.kicksMin,
        line.score
      ))
    }
  }

  case object FormatterAll extends Formatter[Data02] {

    override def header: PrintWriter => Unit = (pw: PrintWriter) => {
      pw.println(defaultHeaders(Seq("id", "nr", "iterations",
        "kicksMax", "kicksMean", "kicksMin", "kickOutMax", "kickOutMean", "kickOutMin",
        "otherGoalsMax", "otherGoalsMean", "otherGoalsMin", "ownGoalsMax", "ownGoalsMean", "ownGoalsMin",
        "goalDifference", "score")))
    }

    override def data: (Data02, PrintWriter) => Unit = (line: Data02, pw: PrintWriter) => {
      pw.println(fmtStr(Seq(S, S, D, F, F, F, F, F, F, F, F, F, F, F, F, F, F)) format(
        line.trainGaId,
        line.trainGaNr,
        line.iterations,

        line.kicksMax,
        line.kicksMean,
        line.kicksMin,
        line.kickOutMax,
        line.kickOutMean,
        line.kickOutMin,

        line.otherGoalsMax,
        line.otherGoalsMean,
        line.otherGoalsMin,
        line.ownGoalsMax,
        line.ownGoalsMean,
        line.ownGoalsMin,

        line.goalDifference,
        line.score
      ))
    }
  }

  override def csvReader: CsvReader[Data02] = new CsvReaderData02
}
