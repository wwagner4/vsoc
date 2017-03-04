package common

import common.Viz._

object VizTryout extends App {

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val sin = (1.0 to (20.0, 0.1)).map(x => XY(x, 5 + math.sin(x) * 2))
  val log = (1.0 to (20.0, 0.1)).map(x => XY(x, math.log(x) * 2.0))

  var dataRows = List(
    DataRow("sin", sin),
    DataRow("log", log)
  )

  val dia = Diagram("a", "Test A", yLabel = Some("label y"), dataRows = dataRows)

  Viz.createDiagram(dia)

}
