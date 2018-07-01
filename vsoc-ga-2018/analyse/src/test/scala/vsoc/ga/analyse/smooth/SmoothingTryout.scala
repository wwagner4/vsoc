package vsoc.ga.analyse.smooth

import java.nio.file.{Files, Paths}

import entelijan.viz.Viz.{DataRow, XY}
import entelijan.viz.{Viz, VizCreator, VizCreators}
import org.slf4j.LoggerFactory

import scala.util.Random

object SmoothingTryout extends App {

  private val log = LoggerFactory.getLogger(SmoothingTryout.getClass)

  val workDir = Paths.get("C:/ta30/tmp")

  val scriptDir = workDir.resolve(".script")
  Files.createDirectories(scriptDir)
  val imgDir = workDir.resolve("dia-grouping")
  Files.createDirectories(imgDir)
  log.info(s"Writing image to '$imgDir'")
  implicit val creator: VizCreator[XY] = VizCreators.gnuplot(scriptDir = scriptDir.toFile, imageDir = imgDir.toFile, clazz=classOf[Viz.XY])

  val data = (0 to 100).map(i => (i, i * 0.05 + Random.nextDouble() * (5 + (i * 0.1))))

  val dataOrig = for ((x, y) <- data) yield Viz.XY(x, y)

  val _dataRows = Seq(1, 5, 20, 100).map { grpSize =>
    val grped = Smoothing.smooth(dataOrig, grpSize)
    DataRow[XY](
      name = Some(grpSize.toString),
      data = grped
    )
  }

  val dia = Viz.Diagram[Viz.XY](
    id = "grouping",
    title = "Grouping Tryout",
    dataRows = _dataRows
  )

  Viz.createDiagram(dia)


}
