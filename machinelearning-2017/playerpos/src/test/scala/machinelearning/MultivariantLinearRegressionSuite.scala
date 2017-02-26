package machinelearning

import org.scalatest._
import Matchers._
import breeze.linalg.{DenseMatrix, _}

class MultivariantLinearRegressionSuite extends FunSuite {

  import GradientDescent._
  import HypothesisFunction._

  val ts01 = TrainingSet(
    X = DenseMatrix(
      (1.0, 0.0),
      (1.0, 0.5),
      (1.0, 1.0),
      (1.0, 2.1),
      (1.0, 4.8)
    ),
    y = DenseMatrix(
      2.1,
      2.2,
      3.2,
      3.0,
      6.1)
  )

  val ts02 = TrainingSet(
    X = DenseMatrix(
      (1.0, 0.0, 0.3, 1.1),
      (1.0, 0.5, 2.3, 0.7),
      (1.0, 1.0, 0.3, 1.2),
      (1.0, 2.1, 4.3, 0.3),
      (1.0, 4.8, 5.2, 2.4),
      (1.0, 2.0, 6.3, 0.7),
      (1.0, 3.5, 7.4, 2.5),
      (1.0, 4.0, 3.5, 3.6),
      (1.0, 2.6, 0.6, 0.7),
      (1.0, 4.7, 0.8, 5.8)),
    y = DenseMatrix(
      2.1,
      2.2,
      3.2,
      3.0,
      6.1,
      2.1,
      2.2,
      3.2,
      3.0,
      6.1)
  )

  {
    val testData = List(
      (DenseMatrix(0.0, 0.0), 6.57),
      (DenseMatrix(1.0, 0.0), 3.75),
      (DenseMatrix(0.0, 1.0), 1.46),
      (DenseMatrix(1.0, 1.0), 0.32),
      (DenseMatrix(1.93, 0.82), 0.07))

    val cf = costFunc(linearFunc)(ts01) _
    testData.foreach { case (t, shouldCost) =>
      test(s"Cost 01 ${format(t)}") {
        cf(t) should be(shouldCost +- 0.01)
      }
    }
  }


  {
    val testData = List(
      (DenseMatrix(0.0, 0.0, 2.9, 4.1), 132.37),
      (DenseMatrix(1.0, 0.0, 4.4, 5.3), 316.01),
      (DenseMatrix(0.0, 1.0, 1.1, 3.3), 60.81),
      (DenseMatrix(1.0, 1.0, 1.0, 1.0), 20.67),
      (DenseMatrix(2.1057, 0.8435, -0.2732, -0.0338), 0.29))

    val cf = costFunc(linearFunc)(ts02) _
    testData.foreach { case (t, shouldCost) =>
      test(s"Cost 02 ${format(t)}") {
        cf(t) should be(shouldCost +- 0.01)
      }
    }
  }

  {
    val expectedValues = Map(
      0 -> DenseMatrix(0.00, 0.00),
      10 -> DenseMatrix(0.99, 1.12),
      20 -> DenseMatrix(1.35, 1.01),
      30 -> DenseMatrix(1.57, 0.94),
      40 -> DenseMatrix(1.71, 0.89),
      50 -> DenseMatrix(1.80, 0.87),
      60 -> DenseMatrix(1.85, 0.85),
      70 -> DenseMatrix(1.88, 0.84),
      80 -> DenseMatrix(1.91, 0.83),
      90 -> DenseMatrix(1.92, 0.83))
    val gd = gradientDescent(0.1)(linearFunc)(ts01) _
    val initialThet = DenseMatrix(0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(100)
      .zipWithIndex
      .filter { case (_, i) => i % 10 == 0 }
      .foreach { case (thet, i) =>
        test(s"Gradient Descent 01 assert step $i") {
          eq(thet, expectedValues(i))
        }
      }
  }

  {
    val expectedValues = Map(
      0 -> DenseMatrix(0.00, 0.00, 0.00, 0.00),
      50 -> DenseMatrix(1.00, 0.77, -0.11, 0.25),
      100 -> DenseMatrix(1.49, 0.83, -0.19, 0.10),
      150 -> DenseMatrix(1.76, 0.85, -0.23, 0.03),
      200 -> DenseMatrix(1.91, 0.85, -0.25, -0.00),
      250 -> DenseMatrix(2.00, 0.85, -0.26, -0.02),
      300 -> DenseMatrix(2.05, 0.85, -0.27, -0.02),
      350 -> DenseMatrix(2.07, 0.85, -0.27, -0.03),
      400 -> DenseMatrix(2.09, 0.84, -0.27, -0.03),
      450 -> DenseMatrix(2.10, 0.84, -0.27, -0.03))

    val gd = gradientDescent(0.05)(linearFunc)(ts02) _
    val initialThet = DenseMatrix(0.0, 0.0, 0.0, 0.0)
    val steps = Stream.iterate(initialThet)(thet => gd(thet))
    steps.take(500)
      .zipWithIndex
      .filter { case (_, i) => i % 50 == 0 }
      .foreach { case (thet, i) =>
        test(s"Gradient Descent 02 assert step $i") {
          eq(thet, expectedValues(i))
        }
      }
  }

  /**
    * Compares two double vectors
    */
  private def eq(is: DenseMatrix[Double], should: DenseMatrix[Double]): Unit = {
    is.rows should be(should.rows)
    is.cols should be(should.cols)
    for (i <- 0 until is.rows; j <- 0 until is.cols) {
      is(i, j) should be(should(i, j) +- 0.01)
    }
  }

  private def format(m: Matrix[Double]): String = {
    val re = for (i <- 0 until m.rows; j <- 0 until m.cols) yield {
      "%.2f" format m(i, j)
    }
    re.mkString("[", ",", "]")
  }

}
