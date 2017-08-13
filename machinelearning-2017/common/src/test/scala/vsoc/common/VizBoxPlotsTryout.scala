package vsoc.common

import Viz._

object VizBoxPlotsTryout extends App {

  implicit val creator: VizCreator[X] = VizCreatorGnuplot[X](UtilIO.dirScripts, UtilIO.dirSub(UtilIO.dirScripts, "test-img"), execute = true)

  case class Gaussian(id: String, mean: Double, vari: Double)

  val ran = new java.util.Random()

  val dataConfList = List(
    Gaussian("Hallo", 2, 5.1),
    Gaussian("wo", -5, 1.1),
    Gaussian("ist", 1, 5.5),
    Gaussian("der", 0, 0.6)
  )

  val data: Seq[DataRow[X]] = dataConfList.map { c =>
    val d = (1 to 100).map { _ =>
      val v = c.mean + ran.nextGaussian() * c.vari
      X(v)
    }
    new DataRow[X](style = Style_BOXPLOT, name = Some(c.id), data = d)
  }

  val diagram = new Diagram[X](
    id= "BP01",
    title="Boxplot Tryout",
    yLabel = Some("Y"),
    xLabel = Some("X"),
    yRange = Some(Viz.Range(Some(-60), Some(60))),
    dataRows = data)

  Viz.createDiagram[X](diagram)

}
