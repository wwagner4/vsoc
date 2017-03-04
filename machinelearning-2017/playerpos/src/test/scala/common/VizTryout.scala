package common

object VizTryout extends App {

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  var dataRows = List(
    Viz.DataRow("a", List(
      Viz.XY(1, 3),
      Viz.XY(2, 1),
      Viz.XY(3, 2),
      Viz.XY(4, 1),
      Viz.XY(5, 2)
    )),
    Viz.DataRow("b", List(
      Viz.XY(1, 3),
      Viz.XY(2, 4),
      Viz.XY(3, 3),
      Viz.XY(4, 2),
      Viz.XY(5, 6)
    ))
  )

  val dia = Viz.Diagram("a", "Test A", "text x", "test y", dataRows)

  Viz.createDiagram(dia)

}
