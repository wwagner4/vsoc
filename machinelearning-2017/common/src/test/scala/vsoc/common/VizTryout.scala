package vsoc.common

import vsoc.common.Viz._

object VizTryout extends App {

  private implicit val creator: VizCreator[XY] = VizCreatorGnuplot[XY](UtilIO.dirScripts, UtilIO.dirScripts)

  val sin = (1.0 to(20.0, 0.1)).map(x => XY(x, 5 + math.sin(x) * 2))
  val log = (1.0 to(20.0, 0.1)).map(x => XY(x, math.log(x) * 2.0))

  val dataRows: Seq[Viz.DataRow[XY]] = List(
    DataRow(name = Some("sin"), data = sin),
    DataRow(name = Some("log"), data = log)
  )

  val dia: Viz.Dia[XY] = Diagram(
    "a",
    "Test A",
    yLabel = Some("label y"),
    xRange = Some(Range(Some(-10), None)),
    yRange = Some(Range(Some(0), Some(10))),
    dataRows = dataRows
  )

  Viz.createDiagram(dia)

}
