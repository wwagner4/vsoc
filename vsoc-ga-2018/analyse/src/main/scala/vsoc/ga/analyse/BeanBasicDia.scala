package vsoc.ga.analyse

import java.nio.file.{Files, Paths}

import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import org.slf4j.LoggerFactory
import vsoc.ga.common.config.{Config, ConfigTrainGa}


object BeanBasicDia {

  private val log = LoggerFactory.getLogger(BeanBasicDia.getClass)

  def run(cfg: Config): Unit = {

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

      val data = BeanBasicReader.read(filePath)
        .map(b => Viz.XY(b.iterations, b.score))

      Viz.DataRow(
        name = Some(s"$id-$nr"),
        style = Viz.Style_LINES,
        data = data
      )

    }
    val dia = Viz.Diagram[Viz.XY](
      id = cfg.id,
      title = s"Configuration ${cfg.id}",
      //yRange = Some(Viz.Range(Some(0), Some(2))),
      dataRows = dataRows
    )

    Viz.createDiagram(dia)
  }
}
