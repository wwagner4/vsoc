package vsoc.ga.analyse

import java.nio.file.{Files, Path, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import org.slf4j.LoggerFactory
import vsoc.ga.analyse.group.Grouping
import vsoc.ga.common.config.{Config, ConfigHelper, ConfigTrainGa}

import scala.collection.JavaConverters._


sealed trait DiaConf

case object DiaConf_SUPRESS_TIMESTAMP extends DiaConf

object Data01Dia {

  private val workDir1 = ConfigHelper.workDir

  private val log = LoggerFactory.getLogger(Data01Dia.getClass)

  def createDiaConfig(
                       cfg: Config,
                       xRange: Option[Viz.Range] = None,
                       yRange: Option[Viz.Range] = None,
                       diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
                       diaDir: Option[Path] = None,
                       dataPoints: Option[Int] = None,
                     ): Unit = {
    implicit val creator: VizCreator[Viz.XY] = createCreator(diaDir)
    val dia: Viz.Dia[Viz.XY] = createDia(cfg.trainings, cfg.id, dataPoints, diaConfs, xRange, yRange, None)
    Viz.createDiagram(dia)
  }

  def createDiaWorkDir(
                        xRange: Option[Viz.Range] = None,
                        yRange: Option[Viz.Range] = None,
                        diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
                        diaDir: Option[Path] = None,
                        dataPoints: Option[Int] = None,
                      ): Unit = {

    def extractTrainings: Seq[Seq[ConfigTrainGa]] = {

      def extractConfigs(trainGaDir: Path): Seq[ConfigTrainGa] = {
        require(Files.isDirectory(trainGaDir))
        val cfgs = Files.list(trainGaDir).iterator().asScala.toSeq.map { nrDir =>
          if (Files.isDirectory(nrDir) && !nrDir.getFileName.toString.startsWith(".")) {
            val cfgName = trainGaDir.getFileName.toString
            val nrName = nrDir.getFileName.toString
            Some(ConfigTrainGa(cfgName, nrName))
          } else None
        }
        cfgs.flatten
      }

      Files.list(workDir1).iterator().asScala.toSeq.map { file =>
        if (Files.isDirectory(file) && file.getFileName.toString.startsWith("train")) extractConfigs(file)
        else Seq.empty[ConfigTrainGa]
      }.filter(s => s.nonEmpty).sortBy(f => f(0).id)
    }

    implicit val creator: VizCreator[Viz.XY] = createCreator(diaDir)

    val trainings = extractTrainings

    val dias: Seq[Viz.Diagram[Viz.XY]] = trainings.zipWithIndex.map {
      case (tr, i) =>
        require(tr.nonEmpty)
        val tit = Some(s"Training Config ${tr(0).id}")
        createDia(tr, s"dia$i", dataPoints, diaConfs, xRange, yRange, tit)
    }

    val dia = Viz.MultiDiagram[Viz.XY](
      id = "all",
      columns = 4,
      diagrams = dias,
      imgWidth = 2000,
      imgHeight = 1500
    )

    Viz.createDiagram(dia)
  }

  private def createCreator(diaDir: Option[Path]): VizCreator[Viz.XY] = {
    val scriptDir = workDir1.resolve(".script")
    Files.createDirectories(scriptDir)
    val imgDir = diaDir.getOrElse(workDir1.resolve("viz_img"))
    Files.createDirectories(imgDir)
    log.info(s"image directory $imgDir")
    log.info(s"script directory $scriptDir")
    VizCreatorGnuplot[Viz.XY](scriptDir = scriptDir.toFile, imageDir = imgDir.toFile)
  }

  private def createDia(
                         trainGas: Seq[ConfigTrainGa],
                         diaId: String,
                         dataPoints: Option[Int],
                         diaConfs: Seq[DiaConf],
                         xRange: Option[Viz.Range],
                         yRange: Option[Viz.Range],
                         title: Option[String]): Viz.Diagram[Viz.XY] = {
    val dataRows = for (ConfigTrainGa(id, nr) <- trainGas) yield {
      def prjDir = workDir1.resolve(Paths.get(id, nr))

      def filePath = workDir1.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val raw = Data01(id, nr, 0, 0) :: Data01CsvReader.read(filePath).toList

      val data = raw.map(b => Viz.XY(b.iterations, b.score))
      val _dataPoints = dataPoints.getOrElse(50)
      require(_dataPoints > 2, "You must define at least 3 data points")
      val grpSize = math.ceil(data.size.toDouble / _dataPoints).toInt
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
      s"$diaId"
    } else {
      val tsfmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
      val ts = tsfmt.format(LocalDateTime.now)
      s"${diaId}_$ts"
    }

    Viz.Diagram[Viz.XY](
      id = _id,
      title =  title.getOrElse(s"Configuration $diaId"),
      xRange = xRange,
      yRange = yRange,
      dataRows = dataRows.flatten
    )
  }
}
