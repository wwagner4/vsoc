package common

import common.Viz._

object Viz3dTryout extends App {

  implicit val creator: VizCreatorGnuplot[XYZ] = VizCreatorGnuplot[XYZ](Util.scriptsDir)

  val data: Seq[XYZ] = for (x <- (0 to 30 by 5); y <- (0 to 40 by 5)) yield {
    val z = x * y;
    XYZ(x, y, z)
  }

  val dia = Diagram[XYZ](
    "a3d",
    "Test 3D",
    xLabel = Some("X"),
    yLabel = Some("Y"),
    xRange = Some(Range(Some(-10), None)),
    yRange = Some(Range(Some(0), Some(10))),
    dataRows = List(DataRow(name="test", data=data))
  )

  Viz.createDiagram(dia)

}
