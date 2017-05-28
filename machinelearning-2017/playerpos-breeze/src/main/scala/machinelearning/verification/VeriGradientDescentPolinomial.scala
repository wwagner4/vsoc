package machinelearning.verification

import breeze.linalg._
import common._
import machinelearning.TrainingSet


/**
  * Polynomial function that should be easily learned
  * with linear gradient descent.
  * Purpose: Test of learning algorithms
  */
object VeriGradientDescentPolinomial {

  implicit val sepa = ","

  import VeriCreateData._
  
  implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

  case class Params(
                     id: String,
                     datasetIndex: Int,
                     grade: Int,
                     alpha: Double,
                     steps: Int,
                     description: String,
                     stepsOfInterest: List[Int]
                   )

  def steps(x: DenseMatrix[Double], y: Matrix[Double], alpha: Double): Stream[DenseMatrix[Double]] = {
    import machinelearning.GradientDescent._
    import machinelearning.HypothesisFunction._


    val ts = TrainingSet(x, y.toDenseMatrix)
    val thetIni = initialTheta(ts)
    val gd = gradientDescent(alpha)(linearFunc)(ts) _
    Stream.iterate(thetIni) { thet => gd(thet) }
  }

  def plot(params: Params): Unit = {
    val ds = datasets(params.datasetIndex)
    val (x, y) = VeriUtil.readDataSet(ds.filename)
    val x1 = VeriUtil.polyExpand(x, params.grade)
    val dataRows = steps(x1, y, params.alpha)
      .take(params.steps)
      .toList
      .zipWithIndex
      .filter { case (_, i) => params.stepsOfInterest.contains(i + 1) }
      .map { case (theta, i) => createDataRow(i, theta) }
    val originalData = createOriginalDataRow(params)
    val dia = Viz.Diagram(
      id = s"lin_reg${params.grade}_${ds.id}",
      title = f"polinomial regression grade ${params.grade} #data: ${ds.size} ${params.description}",
      dataRows = originalData :: dataRows
    )
    Viz.createDiagram(dia)
  }

  def createOriginalDataRow(params: Params): Viz.DataRow = {
    val ds = datasets(params.datasetIndex)
    val (mx, my) = VeriUtil.readDataSet(ds.filename)
    val data = mx.toArray
      .zip(my.toArray)
      .map { case (x, y) => Viz.XY(x, y) }
    val paramStr = common.Formatter.formatLimitatedDense(thetaOrig.toArray)
    Viz.DataRow(
      s"original params:[$paramStr]",
      Viz.Style_POINTS,
      data
    )
  }

  def createDataRow(step: Int, theta: DenseMatrix[Double]): Viz.DataRow = {
    val _data = (-100.0 to(100.0, 5)).map { x => 
      Viz.XY(x, VeriUtil.poly(x)(theta.toDenseVector))
    }
    val paramStr = common.Formatter.formatLimitatedDense(theta.toArray)
    Viz.DataRow(f"step: ${step + 1}%3d params:[$paramStr]", data = _data)
  }
}

object VeriGradientDescentPolinomialPlotDataMain extends App {

  import VeriGradientDescentPolinomial._
  
  // Grade 2
  val paramList01 = List(
    Params("A1", 0, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A2", 1, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A3", 2, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),
    Params("A4", 3, 2, 0.00000001, 400, "theta_1 still increasing", List(5, 200, 400)),

    Params("C1", 2, 2, 0.0000001, 4000, "instable alpha too great", List(5, 1000, 2000, 3000, 4000)),
    Params("C2", 2, 2, 0.00000005, 4000, "theta_1 increasing to 1126. theta_0 0.32", List(5, 1000, 2000, 3000, 4000)),
    Params("C3", 2, 2, 0.00000001, 4000, "theta_1 increasing to 291. theta_0 0.06", List(5, 1000, 2000, 3000, 4000)),
    Params("C4", 2, 2, 0.000000001, 4000, "theta_1 increasing to 30.94", List(5, 1000, 2000, 3000, 4000)),
    Params("C5", 2, 2, 0.0000000001, 4000, "theta_1 increasing to 3.11", List(5, 1000, 2000, 3000, 4000)),
    Params("C6", 2, 2, 0.00000000001, 4000, "theta_1 increasing to 0.31", List(5, 1000, 2000, 3000, 4000))
  )

  // Grade 3
  val paramList02 = List(
    Params("G30", 0, 3, 0.000000000001, 400, "", List(5, 8, 20, 400)),
    Params("G31", 1, 3, 0.00000000001, 400, "", List(5, 8, 20, 400)),
    Params("G32", 2, 3, 0.00000000001, 400, "", List(5, 8, 20, 400))
  )
  
  // Grade 1
  val paramList03 = List(
    Params("G10", 0, 1, 0.0001, 40, "", List(2, 5, 10, 40)),
    Params("G11", 1, 1, 0.0001, 40, "", List(2, 5, 10, 40)),
    Params("G12", 2, 1, 0.0001, 40, "", List(2, 5, 10, 40))
  )

  // Grade 2
  val paramList04 = List(
    Params("G20", 0, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000)),
    Params("G21", 1, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000)),
    Params("G22", 2, 2, 0.000000001, 5000, "", List(5, 30, 100, 5000))
  )

  val all = paramList03 ::: paramList02 ::: paramList04 
  all.foreach { param => plot(param) }

}
