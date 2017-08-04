package vsoc.common

import vsoc.common.Viz.{Diagram, _}

object VizMultiTryout extends App {

  val d = Util.scriptsDir
  implicit val vc = VizCreatorGnuplot[XY](d, execute = true)

  val d1 = (0.0 to(1.0, 0.1)).map(x => XY(x, math.sin(x)))
  val d2 = (0.0 to(1.0, 0.1)).map(x => XY(x, math.pow(x, 2)))

  val drs1 = List(
    DataRow[XY](data = d1)
  )

  val drs2 = List(
    DataRow[XY](data = d2)
  )

  val dias = List(
    Diagram[XY](id = "d1", title = "Diagram 1", dataRows = drs1),
    Diagram[XY](id = "d2", title = "Diagram 2", dataRows = drs2)
  )

  val md = MultiDiagram[XY](id = "multi", columns = 2, diagrams = dias)

  Viz.createDiagram(md)

}
