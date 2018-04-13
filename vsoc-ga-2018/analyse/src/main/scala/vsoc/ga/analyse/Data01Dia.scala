package vsoc.ga.analyse

import java.nio.file.{Files, Path, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import org.slf4j.LoggerFactory
import vsoc.ga.analyse.group.Grouping
import vsoc.ga.common.config.{Config, ConfigTrainGa}


sealed trait DiaConf

case object DiaConf_SUPRESS_TIMESTAMP extends DiaConf

object Data01Dia {

  private val log = LoggerFactory.getLogger(Data01Dia.getClass)

  def run(
           cfg: Config,
           workDir: Path,
           yRange: Option[Viz.Range] = None,
           diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
           diaDir: Option[Path] = None,
         ): Unit = {

    val scriptDir = workDir.resolve(".script")
    Files.createDirectories(scriptDir)
    val imgDir = diaDir.getOrElse(workDir.resolve("viz_img"))
    Files.createDirectories(imgDir)
    log.info(s"Writing image to '$imgDir'")
    implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY](scriptDir = scriptDir.toFile, imageDir = imgDir.toFile)
    val ds = cfg.trainings
    val dataRows: Seq[Option[Viz.DataRow[Viz.XY]]] = for (ConfigTrainGa(id, nr) <- ds) yield {
      def prjDir = workDir.resolve(Paths.get(id, nr))

      def filePath = workDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val raw = Data01(id, nr, 0, 0) :: Data01CsvReader.read(filePath).toList
      val data = raw.map(b => Viz.XY(b.iterations, b.score))

      val grpSize = math.ceil(data.size.toDouble / 50).toInt
      val dataGrouped = Grouping.group(data, grpSize)


      if (data.isEmpty) None
      else Some(
        Viz.DataRow(
          name = Some(s"$id-$nr"),
          style = Viz.Style_LINES,
          data = dataGrouped
        ))
    }

    val _id = if (diaConfs.contains(DiaConf_SUPRESS_TIMESTAMP)) {
      s"${cfg.id}"
    } else {
      val tsfmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
      val ts = tsfmt.format(LocalDateTime.now)
      s"${cfg.id}_$ts"
    }

    val dia = Viz.Diagram[Viz.XY](
      id = _id,
      title = s"Configuration ${cfg.id}",
      yRange = yRange,
      dataRows = dataRows.flatMap(identity(_))
    )

    Viz.createDiagram(dia)
  }
}
