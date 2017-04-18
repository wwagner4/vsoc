package machinelearning.verification

import breeze.linalg.DenseVector
import org.scalatest.FunSuite

class VeriUtilSuite extends FunSuite {

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
