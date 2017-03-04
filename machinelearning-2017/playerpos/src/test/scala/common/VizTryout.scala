package common

object VizTryout extends App {

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val dia = Viz.Diagram("a", "Test A", "text x", "test y")

  Viz.createDiagram(dia)

}
