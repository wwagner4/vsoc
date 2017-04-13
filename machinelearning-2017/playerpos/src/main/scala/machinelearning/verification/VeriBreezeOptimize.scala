package machinelearning.verification

import breeze.linalg._
import breeze.optimize._
import common.Viz.{MultiDiagram, Style_POINTS}
import common._

/**
  * Tryout for the breeze optimize algorithms
  */
object VeriBreezeOptimize {

  val datasets = List(
    (10, "poly_10.txt"),
    (50, "poly_50.txt"),
    (100, "poly_100.txt"),
    (1000, "poly_1000.txt")
  )

  def cost(x: DenseMatrix[Double], y: DenseMatrix[Double])(theta: DenseVector[Double]): Double = {
    val y1 = y.t.toDenseVector
    val m = x.rows
    val h = x * theta
    sum((h - y1) ^:^ 2.0) / (2 * m)
  }


}

class LinRegDiffFunction(x: DenseMatrix[Double], y: DenseMatrix[Double]) extends DiffFunction[DenseVector[Double]] {

  private val y1 = y.t.toDenseVector
  private val m = x.rows

  def calculate(theta: DenseVector[Double]): (Double, DenseVector[Double]) = {
    val h = x * theta
    val c = sum((h - y1) ^:^ 2.0) / (2 * m)
    val d = (x.t * (h - y1)) *:* (1.0 / m)
    (c, d)
  }

}


object VeriBreezeOptimizeStdoutMain extends App {

  import VeriBreezeOptimize._

  val grades = List(2, 3, 4)
  val datasetIndexes = List(0, 1, 2, 3)
  val maxIters = List(2, 5, 6, 7, 8, 9, 10, 20, 50, 60, 70, 80, 90, 100, 150)

  val results = for (grade <- grades; datasetIndex <- datasetIndexes; maxIter <- maxIters) yield {
    val (datasetSize, fname) = datasets(datasetIndex)
    val (x, y) = VeriUtil.readDataSet(fname)
    val x1 = VeriUtil.polyExpand(x, grade)

    val f: DiffFunction[DenseVector[Double]] = new LinRegDiffFunction(x1, y)
    val fa: DiffFunction[DenseVector[Double]] = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

    val thetaInitial = DenseVector.zeros[Double](grade + 1)

    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = 5)
    (grade, datasetSize, maxIter, lbfgs.minimize(f, thetaInitial), lbfgs.minimize(fa, thetaInitial))

  }

  results.zipWithIndex.foreach { case ((grade, size, mi, t, ta), id) =>
    val tStr = common.Formatter.format(t.toArray)
    val taStr = common.Formatter.format(ta.toArray)
    println(f"$id%10d $grade%10d $size%10d $mi%10d | $tStr | $taStr")
  }
}

object VeriBreezeOptimizePlotMain extends App {

  import VeriBreezeOptimize._

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  case class PlotParam(
                        id: String,
                        grade: Int,
                        datasetIndex: Int,
                        diffFunctionType: String,
                        maxIters: List[Int]
                      )

  case class PlotGroup(
                        grade: Int,
                        plots: List[PlotParam]
                      )

  val plotGroups = List(
    PlotGroup(
      grade = 2,
      List(
        plotParam("01", 2, 3, "A"),
        plotParam("02", 2, 3, "E"),
        plotParam("03", 2, 2, "A"),
        plotParam("04", 2, 2, "E"),
        plotParam("05", 2, 1, "A"),
        plotParam("06", 2, 1, "E"),
        plotParam("07", 2, 0, "A"),
        plotParam("08", 2, 0, "E")
      )
    ),
    PlotGroup(
      grade = 3,
      List(
        plotParam("01", 3, 3, "A"),
        plotParam("02", 3, 3, "E"),
        plotParam("03", 3, 2, "A"),
        plotParam("04", 3, 2, "E"),
        plotParam("05", 3, 1, "A"),
        plotParam("06", 3, 1, "E"),
        plotParam("07", 3, 0, "A"),
        plotParam("08", 3, 0, "E")
      )
    ),
    PlotGroup(
      grade = 4,
      List(
        plotParam("01", 4, 3, "A"),
        plotParam("02", 4, 3, "E"),
        plotParam("03", 4, 2, "A"),
        plotParam("04", 4, 2, "E"),
        plotParam("05", 4, 1, "A"),
        plotParam("06", 4, 1, "E"),
        plotParam("07", 4, 0, "A"),
        plotParam("08", 4, 0, "E")
      )
    )
  )


  def plotParam(id: String, grade: Int, dataSetIndex: Int, difFunctionType: String): PlotParam = {
    PlotParam(id = id, grade = grade, datasetIndex = dataSetIndex, diffFunctionType = difFunctionType, maxIters = List(5, 7, 9, 12, 20, 50))
  }

  def dia(param: PlotParam): Viz.Diagram = {
    def dataRow(label: String, theta: DenseVector[Double], style: Viz.Style): Viz.DataRow = {
      val data = (-100.0 to(100.0, 5))
        .map { x => Viz.XY(x, VeriGradientDescentPolinomial.poly(x)(theta.toDenseVector)) }

      Viz.DataRow(
        name = label,
        style = style,
        data = data
      )
    }

    val (datasetSize, fname) = datasets(param.datasetIndex)
    val (x, y) = VeriUtil.readDataSet(fname)
    val x1 = VeriUtil.polyExpand(x, param.grade)

    val diffFunc = param.diffFunctionType match {
      case "E" => new LinRegDiffFunction(x1, y)
      case "A" => new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))
    }

    val results = for (maxIter <- param.maxIters) yield {
      val thetaInitial = DenseVector.zeros[Double](param.grade + 1)
      val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = 5)
      val theta = lbfgs.minimize(diffFunc, thetaInitial)
      (maxIter, theta)
    }

    val dataRows: Seq[Viz.DataRow] = results.map { case (mi, theta) => dataRow(f"iter $mi", theta, Viz.Style_LINES(0.5)) }

    val origRow: Viz.DataRow = dataRow("original", VeriGradientDescentPolinomial.thetaOrig, Viz.Style_POINTS)

    val origData: Viz.DataRow = {
      require(x.rows == y.rows)
      val data = (0 until x.rows).map { i =>
        val vx = x(i, 0)
        val vy = y(i, 0)
        Viz.XY(vx, vy)
      }
      Viz.DataRow(
        name = "data",
        style = Style_POINTS(0.2),
        data = data
      )
    }

    Viz.Diagram(
      id = s"veriopt_${param.id}",
      title = s"Verify Breeze Optimize funcType:${param.diffFunctionType} datasetSize:$datasetSize",
      yRange = Some(Viz.Range(Some(-200000), Some(200000))),
      dataRows = origRow +: origData +: dataRows
    )

  }

  plotGroups.foreach { pg =>

    val dias = pg.plots.map { param => dia(param) }

    val mdia = MultiDiagram(
      id = s"VeriBreezeOpt_${pg.grade}",
      title = Some(s"Grade ${pg.grade}"),
      rows = 4,
      columns = 2,
      imgWidth = 1200,
      imgHeight = 2000,
      diagrams = dias
    )
    Viz.createDiagram(mdia)
  }

  /*
  val spez1 =
    PlotParam(id = "spez1",
      grade = 3, datasetIndex = 1,
      diffFunctionType = "E",
      maxIters = List(1, 2, 3, 5, 500, 1000, 2000)
    )
  val diaSpez1 = dia(spez1)
  Viz.createDiagram(diaSpez1)

  val spez2 =
    PlotParam(id = "spez2",
      grade = 3, datasetIndex = 2,
      diffFunctionType = "E",
      maxIters = List(1, 2, 3, 5, 500, 1000, 2000)
    )
  val diaSpez2 = dia(spez2)
  Viz.createDiagram(diaSpez2)
  */

}

object CompareToApproximationMain extends App {

  import VeriBreezeOptimize._

  val grade = 3

  val (_, fname) = datasets(0)
  val (x, y) = VeriUtil.readDataSet(fname)
  val x1 = VeriUtil.polyExpand(x, grade)

  val f = new LinRegDiffFunction(x1, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

  val th = DenseVector(0.001, 0.1, -0.02, 0.01)

  val t11 = f.calculate(th)
  val t12 = fa.calculate(th)

  println(t11)
  println(t12)


}


