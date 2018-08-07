package vsoc.ga.analyse

import java.nio.file.{Files, Path}

import entelijan.viz.{Viz, VizCreator, VizCreators}
import org.slf4j.LoggerFactory
import vsoc.ga.common.config.ConfigHelper

//sealed trait DiaConf

//case object DiaConf_SUPRESS_TIMESTAMP extends DiaConf

class DataDia[T](csvReader: CsvReader[T]) {

  private val log = LoggerFactory.getLogger(classOf[DataDia[_]])

  val _workDir = ConfigHelper.workDir

  def createDiaTrainGa(trainGa: String, diaFactory: DiaFactory[T], diaDir: Option[Path] = None): Unit = {
    implicit val crea = createCreator(diaDir)
    val data = csvReader.read(trainGa)
    val dia = diaFactory.createDia(data)
    Viz.createDiagram(dia)
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

}
