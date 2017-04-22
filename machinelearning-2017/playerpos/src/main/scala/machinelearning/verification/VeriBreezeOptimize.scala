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

  val m = 5

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

  def cost(x: DenseMatrix[Double], y: DenseVector[Double])(theta: DenseVector[Double]): Double = {
    val m = x.rows
    val h = x * theta
    sum((h - y) ^:^ 2.0) / (2 * m)
  }

  class LinRegDiffFunction(x: DenseMatrix[Double], y: DenseVector[Double]) extends DiffFunction[DenseVector[Double]] {

    private val m = x.rows

    def calculate(theta: DenseVector[Double]): (Double, DenseVector[Double]) = {
      val h = x * theta
      val c = sum((h - y) ^:^ 2.0) / (2 * m)
      val d = (x.t * (h - y)) *:* (1.0 / m)
      (c, d)
    }
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
      val data = (VeriCreateData.min to(VeriCreateData.max, VeriCreateData.steps)).map { x =>
        val y = VeriUtil.poly(x)(theta.toDenseVector)
        Viz.XY(x, y)
      }

      Viz.DataRow(
        name = label,
        style = style,
        data = data
      )
    }

    val (x, y) = VeriUtil.readDataSet(param.ds.filename)
    val y1 = y.t.toDenseVector
    val x1 = VeriUtil.polyExpand(x, param.grade)

    val diffFunc = param.diffFunctionType match {
      case DiffFunctionType_APPROX => new LinRegDiffFunction(x1, y1)
      case DiffFunctionType_EXPL => new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y1)(_))
    }

    val results = for (maxIter <- param.maxIters) yield {
      val thetaInitial = DenseVector.zeros[Double](param.grade + 1)
      val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = m)
      val theta = lbfgs.minimize(diffFunc, thetaInitial)
      (maxIter, theta)
    }

    val dataRows: Seq[Viz.DataRow] = results.map { case (mi, theta) => dataRow(f"iter $mi", theta, Viz.Style_LINES(1.0)) }

    val origRow: Viz.DataRow = dataRow("original", VeriCreateData.thetaOrig, Viz.Style_LINESDASHED(1.5))

    val origData: Viz.DataRow = {
      require(x.rows == y.rows)
      val data = (0 until x.rows).map { i =>
        Viz.XY(x(i, 0), y(i, 0))
      }
      Viz.DataRow(
        name = "data",
        style = Style_POINTS(0.5),
        data = data
      )
    }

    Viz.Diagram(
      id = s"${param.ds.id}_${param.diffFunctionType.id}",
      title = s"randStrat:${param.ds.randStrat.name} funcType:${param.diffFunctionType.name} size:${param.ds.size}",
      dataRows = origRow +: origData +: dataRows
    )

  }

}


object VeriBreezeOptimizeStdoutMain extends App {

  import VeriBreezeOptimize._

  val grades = List(1, 2, 3, 4, 5)
  val datasets = VeriCreateData.datasets
  val maxIters = List(5, 100, 1000)

  val results = for (grade <- grades; ds <- datasets; maxIter <- maxIters) yield {
    val (x, y) = VeriUtil.readDataSet(ds.filename)
    val y1 = y.t.toDenseVector
    val x1 = VeriUtil.polyExpand(x, grade)

    val f: DiffFunction[DenseVector[Double]] = new LinRegDiffFunction(x1, y1)
    val fa: DiffFunction[DenseVector[Double]] = new ApproximateGradientFunction[Int, DenseVector[Double]](cost(x1, y1)(_))

    val thetaInitial = DenseVector.zeros[Double](grade + 1)

    val lbfgs = new LBFGS[DenseVector[Double]](maxIter = maxIter, m = m)
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

  val grades = List(2, 3, 4, 5)
  val iterations = List(2, 5, 10, 20, 50, 100)
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

  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  val x = DenseMatrix((1.0, 2.0, 3.0), (3.0, 2.0, -1.0))
  val y = DenseVector(2.2, 1.1)

  val fe = new VeriBreezeOptimize.LinRegDiffFunction(x, y)
  val fa = new ApproximateGradientFunction[Int, DenseVector[Double]](VeriBreezeOptimize.cost(x, y)(_))

  val ranges1 = List(
    (20000, -10000.6745 to(10000.0, 2000.98729)),
    (10000, -5000.987 to(5000.0, 1000.998797)),
    (2000, -1000.345 to(1000.0, 200.238729)),
    (200, -100.23 to(100.0, 20.3389)),
    (20, -10.563 to(10.0, 2.1329)),
    (2, -1.563 to(1.0, 0.087329))
  )

  val ranges2 = List(
    (100, -50.987 to(50.0, 5.998797)),
    (50, -25.345 to(25.0, 5.238729)),
    (20, -10.23 to(10.0, 2.3389)),
    (10, -5.563 to(5.0, 0.5329)),
    (2, -1.563 to(1.0, 0.087329))
  )

  val ranges = ranges1

  val maxs = ranges.map { case (rangeVal, range) =>
    val diffs = for (a <- range; b <- range; c <- range) yield {
      val theta = DenseVector(a, b, c)
      val (_, de) = fe.calculate(theta)
      val (_, da) = fa.calculate(theta)
      math.abs(sum(da - de)) / rangeVal
    }
    (rangeVal, max(diffs))
  }
  println(maxs)

  def xy(vals: Seq[(Int, Double)]): Seq[Viz.XY] = {
    vals.map { case (rv, v) => Viz.XY(rv, v) }
  }

  val dia = Viz.Diagram(
    s"diff_exp_approximate",
    s"Difference explicite to approximate derivatives",
    dataRows = List(Viz.DataRow("diff", data = xy(maxs)))
  )

  Viz.createDiagram(dia)

}


