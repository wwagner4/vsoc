package vsoc.ga.analyse

import java.nio.file.{Files, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import org.slf4j.LoggerFactory
import vsoc.ga.common.config.{Config, ConfigTrainGa}


object Data01Dia {

  private val log = LoggerFactory.getLogger(Data01Dia.getClass)

  def run(cfg: Config, yRange: Option[Viz.Range] = None, filterFactor: Int = 1, minIter: Int = 0): Unit = {

    val workDir = cfg.workDirBase
    val sd = workDir.resolve(".script")
    Files.createDirectories(sd)
    val id = workDir.resolve("viz_img")
    Files.createDirectories(id)
    log.info(s"Writing image to '$id'")
    implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY](scriptDir = sd.toFile, imageDir = id.toFile)
    val ds = cfg.trainings
    val dataRows = for (ConfigTrainGa(id, nr) <- ds) yield {
      def prjDir = workDir.resolve(Paths.get(id, nr))

      def filePath = workDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val data = Data01CsvReader.read(filePath)
        .filter(b => b.iterations % filterFactor == 0 && b.iterations >= minIter)
        .map(b => Viz.XY(b.iterations, b.score))

      Viz.DataRow(
        name = Some(s"$id-$nr"),
        style = Viz.Style_LINES,
        data = data
      )

    }
    val tsfmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val ts = tsfmt.format(LocalDateTime.now)

    val dia = Viz.Diagram[Viz.XY](
      id = s"${cfg.id}_$ts",
      title = s"Configuration ${cfg.id}",
      yRange = yRange,
      dataRows = dataRows
    )

    Viz.createDiagram(dia)
  }
}
