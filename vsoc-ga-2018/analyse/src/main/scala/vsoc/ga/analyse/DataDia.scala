package vsoc.ga.analyse

import java.nio.file.{Files, Path, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import entelijan.viz.{Viz, VizCreator, VizCreators}
import org.slf4j.LoggerFactory
import vsoc.ga.analyse.smooth.Smoothing
import vsoc.ga.common.config.{Config, ConfigHelper, ConfigTrainGa}

import scala.collection.JavaConverters._

sealed trait DiaConf

case object DiaConf_SUPRESS_TIMESTAMP extends DiaConf

abstract class DataDia[T](csvReader: CsvReader[T]) {

  private val _workDir = ConfigHelper.workDir

  private val log = LoggerFactory.getLogger(classOf[DataDia[_]])

  def createDiaConfig(
                       cfg: Config,
                       xRange: Option[Viz.Range] = None,
                       yRange: Option[Viz.Range] = None,
                       diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
                       diaDir: Option[Path] = None,
                       dataPoints: Int = 50,
                     ): Unit = {
    implicit val creator: VizCreator[Viz.XY] = createCreator(diaDir)
    val dia: Viz.Dia[Viz.XY] = createDia(cfg.trainings, cfg.id, dataPoints, diaConfs, xRange, yRange, None)
    Viz.createDiagram(dia)
  }

  def createDiaTrainGa(
                        trainGa: String,
                        xRange: Option[Viz.Range] = None,
                        yRange: Option[Viz.Range] = None,
                        diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
                        diaDir: Option[Path] = None,
                        dataPoints: Option[Int] = None,
                      ): Unit = {
    val dir = _workDir.resolve(trainGa)
    val configs: Seq[ConfigTrainGa] = extractConfigs(dir)
    val diaId = configs(0).id
    createDia(configs, diaId, dataPoints=5, diaConfs=diaConfs, xRange=xRange, yRange=yRange, title=Some(diaId))
  }

  def createDiaWorkDir(
                        id: String,
                        columns: Int = 4,
                        xRange: Option[Viz.Range] = None,
                        yRange: Option[Viz.Range] = None,
                        diaConfs: Seq[DiaConf] = Seq.empty[DiaConf],
                        diaDir: Option[Path] = None,
                        dataPoints: Int = 50,
                        excludes: Seq[String] = Seq.empty[String],
                        includes: Option[Seq[String]] = None
                      ): Unit = {

    val _imgWidth = 3000
    val _imgHeight = 1500

    def resultDirsDia: Seq[Path] = {
      resultDirs.filter { file =>
        def excluded: Boolean = {
          val dirName = file.getFileName.toString
          excludes.contains(dirName)
        }

        def included: Boolean = {
          val dirName = file.getFileName.toString
          includes.forall(is => is.contains(dirName))
        }

        included && !excluded
      }
    }


    def extractTrainings: Seq[Seq[ConfigTrainGa]] = {
      resultDirsDia
        .map(file => extractConfigs(file))
        .filter(s => s.nonEmpty)
        .sortBy(f => f(0).id)
    }

    implicit val creator: VizCreator[Viz.XY] = createCreator(diaDir)

    val trainings: Seq[Seq[ConfigTrainGa]] = extractTrainings

    val dias: Seq[Viz.Diagram[Viz.XY]] = trainings.zipWithIndex.map {
      case (tr, i) =>
        require(tr.nonEmpty)
        val tit = Some(s"Training Config ${tr(0).id}")
        createDia(tr, s"dia$i", dataPoints, diaConfs, xRange, yRange, tit)
    }

    val dia = Viz.MultiDiagram[Viz.XY](
      id = s"all_$id",
      columns = columns,
      diagrams = dias,
      imgWidth = _imgWidth,
      imgHeight = _imgHeight
    )

    Viz.createDiagram(dia)
  }

  private def extractConfigs(trainGaDir: Path): Seq[ConfigTrainGa] = {
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

  private def resultDirs: Seq[Path] = {
    Files.list(_workDir).iterator().asScala
      .toSeq
      .filter(Files.isDirectory(_))
      .filter(_.getFileName.toString.startsWith("train"))
  }

  private def createCreator(diaDir: Option[Path]): VizCreator[Viz.XY] = {
    val scriptDir = _workDir.resolve(".script")
    Files.createDirectories(scriptDir)
    val imgDir = diaDir.getOrElse(_workDir.resolve("viz_img"))
    Files.createDirectories(imgDir)
    log.info(s"image directory $imgDir")
    log.info(s"script directory $scriptDir")
    VizCreators.gnuplot(scriptDir = scriptDir.toFile, imageDir = imgDir.toFile, clazz = classOf[Viz.XY])
  }

  def origin(id: String, nr: String): T
  def x(data: T): Double
  def y(data: T): Double

  private def createDia(
                         trainGas: Seq[ConfigTrainGa],
                         diaId: String,
                         dataPoints: Int,
                         diaConfs: Seq[DiaConf],
                         xRange: Option[Viz.Range],
                         yRange: Option[Viz.Range],
                         title: Option[String]): Viz.Diagram[Viz.XY] = {
    val dataRows = for (ConfigTrainGa(id, nr) <- trainGas) yield {
      def prjDir = _workDir.resolve(Paths.get(id, nr))

      def filePath = _workDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val raw: Seq[T] = origin(id, nr) :: csvReader.read(filePath).toList

      val data = raw.map(b => Viz.XY(x(b), y(b)))
      require(dataPoints > 2, "You must define at least 3 data points")
      val grpSize = math.ceil(data.size.toDouble / dataPoints).toInt
      val dataGrouped = Smoothing.smooth(data, grpSize)

      if (data.isEmpty) None
      else {
        val name = s"$id-$nr"
        Some(
          Viz.DataRow(
            name = Some("%s".format(name)),
            style = Viz.Style_LINES,
            data = dataGrouped
          ))
      }
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
      title = title.getOrElse(s"Configuration $diaId"),
      xRange = xRange,
      yRange = yRange,
      dataRows = dataRows.flatten.sortBy(dr => dr.name.get)
    )
  }


}
