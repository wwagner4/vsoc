package machinelearning.verification

import breeze.linalg._
import breeze.optimize._
import common.Viz.{MultiDiagram, Style_POINTS}
import common._
import machinelearning.verification.VeriCreateData.DataSet


/**
  * Tryout for the breeze optimize algorithms
  */
object VeriBreezeOptimize {

  trait DiffFunctionType {
    def id: String
    def name: String
  }

  case object DiffFunctionType_EXPL extends DiffFunctionType {
    def id = "E"
    def name = "explicit"
  }

  case object DiffFunctionType_APPROX extends DiffFunctionType {
    def id = "A"
    def name = "approximate"
  }

  case class PlotParam(
                        ds: DataSet,
                        diffFunctionType: DiffFunctionType,
                        grade: Int,
                        maxIters: List[Int]
                      )

  case class PlotGroup(
                        id: String,
                        title: String,
                        plots: List[PlotParam]
                      )

  def cost(x: DenseMatrix[Double], y: DenseMatrix[Double])(theta: DenseVector[Double]): Double = {
    val y1 = y.t.toDenseVector
    val m = x.rows
    val h = x * theta
    sum((h - y1) ^:^ 2.0) / (2 * m)
  }

  def multiDiagram(pg: PlotGroup): MultiDiagram = {
    val cols = 4
    MultiDiagram(
      id = pg.id,
      columns = cols,
      imgWidth = cols * 700,
      imgHeight = 2000,
      title = Some(pg.title),
      diagrams = pg.plots.map(p => dia(p))
    )
  }

  def dia(param: PlotParam): Viz.Diagram = {

    def dataRow(label: String, theta: DenseVector[Double], style: Viz.Style): Viz.DataRow = {
      val data = (-100.0 to(100.0, 5)).map { x =>
        val y = VeriUtil.poly(x)(theta.toDenseVector)
        Viz.XY(x, y) }

      Viz.DataRow(
        name = label,
        style = style,
        data = data
      )
    }

    val (x, y) = VeriUtil.readDataSet(param.ds.filename)
    val x1 = VeriUtil.polyExpand(x, param.grade)

    val diffFunc = param.diffFunctionType match {
      case DiffFunctionType_APPROX => new LinRegDiffFunction(x1, y)
      case DiffFunctionType_EXPL => new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))
    }

    val results = for (maxIter <- param.maxIters) yield {
      val thetaInitial = DenseVector.zeros[Double](param.grade + 1)
      val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = 5)
      val theta = lbfgs.minimize(diffFunc, thetaInitial)
      (maxIter, theta)
    }

    val dataRows: Seq[Viz.DataRow] = results.map { case (mi, theta) => dataRow(f"iter $mi", theta, Viz.Style_LINES(0.5)) }

    val origRow: Viz.DataRow = dataRow("original", VeriCreateData.thetaOrig, Viz.Style_POINTS)

    val origData: Viz.DataRow = {
      require(x.rows == y.rows)
      val data = (0 until x.rows).map { i =>
        Viz.XY(x(i, 0), y(i, 0))
      }
      Viz.DataRow(
        name = "data",
        style = Style_POINTS(0.2),
        data = data
      )
    }

    Viz.Diagram(
      id = s"${param.ds.id}_${param.diffFunctionType.id}",
      title = s"randStrat:${param.ds.randStrat.name} funcType:${param.diffFunctionType.name} size:${param.ds.size}",
      yRange = Some(Viz.Range(Some(-100000), Some(100000))),
      dataRows = origRow +: origData +: dataRows
    )

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
  val datasets = VeriCreateData.datasets
  val maxIters = List(10, 20, 30, 32, 33, 34, 35, 37, 40, 50, 60, 70, 80, 90, 100, 150)

  val results = for (grade <- grades; ds <- datasets; maxIter <- maxIters) yield {
    val (x, y) = VeriUtil.readDataSet(ds.filename)
    val x1 = VeriUtil.polyExpand(x, grade)

    val f: DiffFunction[DenseVector[Double]] = new LinRegDiffFunction(x1, y)
    val fa: DiffFunction[DenseVector[Double]] = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

    val thetaInitial = DenseVector.zeros[Double](grade + 1)

    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = 5)
    (grade, ds.size, maxIter, lbfgs.minimize(f, thetaInitial), lbfgs.minimize(fa, thetaInitial))

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

  val grades = List(2, 3, 4, 5, 6, 7)
  val iterations = List(5, 10, 30, 50, 100, 500)
  val diffFunctionTypes = List(DiffFunctionType_EXPL, DiffFunctionType_APPROX)
  val datasets = VeriCreateData.datasets.reverse

  val plotGroups = grades.foreach { grade =>
    val plotParams = for (ds <- datasets; diffFunctionType <- diffFunctionTypes) yield {
      PlotParam(ds, diffFunctionType, grade, iterations)
    }

    val pg = PlotGroup(
      id = s"grades_$grade",
      title = s"grade $grade",
      plots = plotParams
    )
    val mdia = multiDiagram(pg)
    Viz.createDiagram(mdia)
  }
}

object CompareToApproximationMain extends App {

  import VeriBreezeOptimize._

  val grade = 3

  val ds = VeriCreateData.datasets(0)
  val (x, y) = VeriUtil.readDataSet(ds.filename)
  val x1 = VeriUtil.polyExpand(x, grade)

  val f = new LinRegDiffFunction(x1, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y)(_))

  val th = DenseVector(0.001, 0.1, -0.02, 0.01)

  val t11 = f.calculate(th)
  val t12 = fa.calculate(th)

  println(t11)
  println(t12)


}


