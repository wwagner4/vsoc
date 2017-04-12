package machinelearning.verification

import breeze.linalg._
import breeze.optimize._
import common.Viz.MultiDiagram
import common.{Util, Viz, VizCreatorGnuplot}

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

  def dataRow(maxIter: Int, theta: DenseVector[Double]): Viz.DataRow = {
    val data = (-100.0 to(100.0, 5))
      .map { x => Viz.XY(x, VeriGradientDescentPolinomial.poly(x)(theta.toDenseVector)) }
    Viz.DataRow(f"iter:$maxIter%3d", data = data)
  }

  val params = List(
    PlotParam(id = "A", grade = 3, datasetIndex = 2, diffFunctionType = "E", maxIters = List(2, 5, 6, 7, 8, 9, 10, 20, 50, 60, 70, 80, 100)),
    PlotParam(id = "B", grade = 3, datasetIndex = 2, diffFunctionType = "A", maxIters = List(2, 5, 6, 7, 8, 9, 10, 20, 50, 60, 70, 80, 100))
  )

  val dias = params.map { param =>
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

    val dataRows: Seq[Viz.DataRow] = results.map { case (mi, theta) => dataRow(mi, theta) }
    Viz.Diagram(
      id = s"veriopt_${param.id}",
      title = s"Verify Breeze Optimize funcType:${param.diffFunctionType} grade:${param.grade} datasetSize:$datasetSize",
      dataRows = dataRows
    )
  }

  val mdia = MultiDiagram(
    id = "VeriBreezeOpt",
    rows = 2,
    columns = 1,
    imgWidth = 1200,
    diagrams = dias
  )

    Viz.createDiagram(mdia)

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


