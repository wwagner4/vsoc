package vsoc.ga.analyse

import java.nio.file.Paths

import entelijan.viz.{DefaultDirectories, Viz, VizCreator, VizCreatorGnuplot}

object BeanBasicDia extends App {

  val id = "???" // e.g. trainGaKicks01, trainGa01, ...

  val nr = "???" // e.g. 001, 002, 003, ...



  def homeDir = Paths.get(System.getProperty("user.home"))
  def workDir = Paths.get("work", "work-vsoc-ga-2018")

  val vizDir = DefaultDirectories(workDir.resolve("viz").toString)
  implicit val creator: VizCreator[Viz.XY] = VizCreatorGnuplot[Viz.XY](scriptDir= vizDir.scriptDir, imageDir = vizDir.imageDir)

  def prjDir = workDir.resolve(Paths.get(id, nr))
  def filePath = homeDir.resolve(prjDir.resolve(Paths.get(s"data-basic.csv")))

  val data = BeanBasicReader.read(filePath)
    .map(b => Viz.XY(b.iterations, b.score))

  val dataRow = Viz.DataRow(
    style = Viz.Style_LINES,
    data = data,
  )

  val dia = Viz.Diagram[Viz.XY](
    id = s"${id}_$nr",
    title = s"$id $nr",
    //yRange = Some(Viz.Range(Some(0), Some(2))),
    dataRows = Seq(dataRow)
  )

  Viz.createDiagram(dia)

}
