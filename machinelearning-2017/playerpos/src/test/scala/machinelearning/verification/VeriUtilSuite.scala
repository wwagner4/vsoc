package machinelearning.verification

import breeze.linalg.DenseVector
import org.scalatest.FunSuite

class VeriUtilSuite extends FunSuite {

  {
    val theta = DenseVector(0.0, 1.0)
    val xs = -100 to(100, 5)
    for (x <- xs) {
      val xd = x.toDouble
      test(f"poli equal $xd%.3f") {
        val y = VeriUtil.poly(xd)(theta)
        assert(y === xd)
      }
    }
  }
  {
    val theta = DenseVector(1.0, 2.0)
    val poly_1_2: Double => Double = VeriUtil.poly(_)(theta)

    val xys = List(
      (0.0, 1.0),
      (1.0, 3.0),
      (2.0, 5.0),
      (-2.0, -3.0),
      (5.0, 11.0)
    )

    xys.foreach { case (x, y) =>
      test(f"poly_1_2 $x%.1f") {
        assert(poly_1_2(x) === y)
      }
    }
  }

  {
    val theta = DenseVector(1.0, 2.0, 3.0)
    val poly_1_2_3: Double => Double = VeriUtil.poly(_)(theta)

    val xys = List(
      (0.0, 1.0),
      (1.0, 6.0),
      (2.0, 17.0),
      (3.0, 34.0),
      (5.0, 86.0),
      (-2.0, 9.0)
    )

    xys.foreach { case (x, y) =>
      test(f"poly_1_2_3 $x%.1f") {
        assert(poly_1_2_3(x) === y)
      }
    }
  }

}
