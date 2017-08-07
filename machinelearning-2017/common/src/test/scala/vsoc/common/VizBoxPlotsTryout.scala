package vsoc.common

import Viz._

object VizBoxPlotsTryout extends App {

  implicit val creator = VizCreatorGnuplot[XY](UtilIO.dirScripts, UtilIO.dirSub(UtilIO.dirScripts, "test-img"), true)

  case class Gaussian(mean: Double, vari: Double)

  val ran = new java.util.Random()

  val dataConfList = List(
    Gaussian(2, 5.1),
    Gaussian(-5, 1.1),
    Gaussian(1, 5.5),
    Gaussian(0, 0.6)
  )

  val data: Seq[DataRow[XY]] = dataConfList.map { c =>
    val d = (1 to 100).map { n =>
      val v = c.mean + ran.nextGaussian() * c.vari
      XY(n, v)
    }
    new DataRow[XY](style = Style_BOXPLOT, data = d)
  }

  val diagram = new Diagram[XY](id= "BP01", title="Boxplot Tryout", dataRows = data)

  Viz.createDiagram[XY](diagram)

}
