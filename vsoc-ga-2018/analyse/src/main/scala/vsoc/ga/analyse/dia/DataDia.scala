package vsoc.ga.analyse.dia

import java.nio.file.{Files, Path}

import entelijan.viz.{Viz, VizCreator, VizCreators}
import org.slf4j.LoggerFactory
import vsoc.ga.common.config.ConfigHelper

//sealed trait DiaConf

//case object DiaConf_SUPRESS_TIMESTAMP extends DiaConf

class DataDia[T](csvReader: CsvReader[T]) {

  private val log = LoggerFactory.getLogger(classOf[DataDia[_]])

  def createDiaTrainGa(trainGa: String, diaFactory: DiaFactory[T], workDir: Path, diaDir: Option[Path] = None): Unit = {
    implicit val crea: VizCreator[Viz.XY] = createCreator(workDir, diaDir)
    val data = csvReader.read(trainGa)
    val dia = diaFactory.createDia(trainGa, data)
    Viz.createDiagram(dia)
  }

  private def createCreator(workDir: Path, diaDir: Option[Path]): VizCreator[Viz.XY] = {
    val scriptDir = workDir.resolve(".script")
    Files.createDirectories(scriptDir)
    val imgDir = diaDir.getOrElse(workDir.resolve("dias"))
    Files.createDirectories(imgDir)
    log.info(s"image directory $imgDir")
    log.info(s"script directory $scriptDir")
    VizCreators.gnuplot(scriptDir = scriptDir.toFile, imageDir = imgDir.toFile, clazz = classOf[Viz.XY])
  }

}
