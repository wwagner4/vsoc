package vsoc.ga.analyse

import java.nio.file.{Path, Paths}

import entelijan.viz.{DefaultDirectories, Viz, VizCreator, VizCreatorGnuplot}
import org.slf4j.LoggerFactory


class BeanBasicDia(val homeDir: Path) {

  private val log = LoggerFactory.getLogger(classOf[BeanBasicDia])

  private def workDir = Paths.get("work", "work-vsoc-ga-2018")
  val vizDir = DefaultDirectories(workDir.resolve("viz").toString)
  implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY](scriptDir = vizDir.scriptDir, imageDir = vizDir.imageDir)


  def run(dataGa: DataGa): Unit = dataGa match {
    case DataGa_One(id, nr) => oneDia(id, nr)
    case DataGa_Multi(id, title, dataList) => multiDataDia(id, title, dataList)
  }

  private def oneDia(id: String, nr: String): Unit = {

    def prjDir = workDir.resolve(Paths.get(id, nr))
    def filePath = homeDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

    log info s"Reading data from $filePath"

    val data = BeanBasicReader.read(filePath)
      .map(b => Viz.XY(b.iterations, b.score))

    require(data.nonEmpty, "data must not be empty")

    val dataRow = Viz.DataRow(
      style = Viz.Style_LINES,
      data = data
    )

    val dia = Viz.Diagram[Viz.XY](
      id = s"${id}_$nr",
      title = s"$id $nr",
      //yRange = Some(Viz.Range(Some(0), Some(2))),
      dataRows = Seq(dataRow)
    )

    Viz.createDiagram(dia)
  }
  private def multiDataDia(idDia: String, titleDia: String, ds: Seq[(String, String)]): Unit = {

    val dataRows = for ((id, nr) <- ds ) yield {
      def prjDir = workDir.resolve(Paths.get(id, nr))

      def filePath = homeDir.resolve(prjDir.resolve(Paths.get(s"$id-$nr-data.csv")))

      val data = BeanBasicReader.read(filePath)
        .map(b => Viz.XY(b.iterations, b.score))

      Viz.DataRow(
        name = Some(nr),
        style = Viz.Style_LINES,
        data = data
      )

    }
    val dia = Viz.Diagram[Viz.XY](
      id = idDia,
      title = titleDia,
      //yRange = Some(Viz.Range(Some(0), Some(2))),
      dataRows = dataRows
    )

    Viz.createDiagram(dia)
  }
}
