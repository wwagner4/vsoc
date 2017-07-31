package common

import common.Viz.{DataRow, _}

object Viz3dTryout extends App {

  implicit val creator: VizCreatorGnuplot[XYZ] = VizCreatorGnuplot[XYZ](Util.scriptsDir)

  val data01: Seq[XYZ] = for (x <- (0.0 to 30.0 by 1); y <- (0.0 to 40.0 by 1)) yield {
    val z = (x * y) -200
    XYZ(x, y, z)
  }

  val data02: Seq[XYZ] = for (x <- (0 to 30 by 5); y <- (0 to 40 by 5)) yield {
    val z = 0;
    XYZ(x, y, z)
  }

  val dia = Diagram[XYZ](
    "a3d",
    "Test 3D",
    xLabel = Some("X"),
    yLabel = Some("Y"),
    zLabel = Some("Z"),
    xRange = Some(Range(Some(-5), Some(35))),
    yRange = Some(Range(Some(-5), Some(45))),
    zRange = Some(Range(Some(-100), Some(1000))),
    xyGrid = 50,
    dataRows = List(
      DataRow(data = data01),
      DataRow(data = data02))
  )

  Viz.createDiagram(dia)

}
