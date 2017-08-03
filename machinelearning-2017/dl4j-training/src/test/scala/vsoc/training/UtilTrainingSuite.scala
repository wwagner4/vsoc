package vsoc.training

import common.Viz
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.scalatest._
import Matchers._

class UtilTrainingSuite extends FunSuite {

  import UtilTraining._

  val eps = 0.000001

  test("convert to xy 0 1") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8, 4.4),
      Array(1.2, 2.7, 3.9, 4.4),
      Array(1.3, 2.6, 3.0, 4.4),
      Array(1.4, 2.5, 3.2, 4.5)
    ))
    val out = convertXY(array, (0, 1))
    assert(out.size === 4)

    out(0).x.doubleValue() should equal(1.0 +- eps)
    out(0).y.doubleValue() should equal(2.0 +- eps)

    out(1).x.doubleValue() should equal(1.2 +- eps)
    out(1).y.doubleValue() should equal(2.7 +- eps)

    out(2).x.doubleValue() should equal(1.3 +- eps)
    out(2).y.doubleValue() should equal(2.6 +- eps)

    out(3).x.doubleValue() should equal(1.4 +- eps)
    out(3).y.doubleValue() should equal(2.5 +- eps)
  }

  test("convert to xyz 0 1 3") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8, 4.4),
      Array(1.2, 2.7, 3.9, 4.4),
      Array(1.3, 2.6, 3.0, 4.4),
      Array(1.4, 2.5, 3.2, 4.5)
    ))
    val out = convertXYZ(array, (0, 1, 3))
    assert(out.size === 4)

    out(0).x.doubleValue() should equal(1.0 +- eps)
    out(0).y.doubleValue() should equal(2.0 +- eps)
    out(0).z.doubleValue() should equal(4.4 +- eps)

    out(1).x.doubleValue() should equal(1.2 +- eps)
    out(1).y.doubleValue() should equal(2.7 +- eps)
    out(1).z.doubleValue() should equal(4.4 +- eps)

    out(2).x.doubleValue() should equal(1.3 +- eps)
    out(2).y.doubleValue() should equal(2.6 +- eps)
    out(2).z.doubleValue() should equal(4.4 +- eps)

    out(3).x.doubleValue() should equal(1.4 +- eps)
    out(3).y.doubleValue() should equal(2.5 +- eps)
    out(3).z.doubleValue() should equal(4.5 +- eps)
  }

  test("convert to xyz 0 1 2") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8, 4.4),
      Array(1.2, 2.7, 3.9, 4.4),
      Array(1.3, 2.6, 3.0, 4.4),
      Array(1.4, 2.5, 3.2, 4.5)
    ))
    val out = convertXYZ(array, (0, 1, 2))

    assert(out.size === 4)

    out(0).x.doubleValue() should equal(1.0 +- eps)
    out(0).y.doubleValue() should equal(2.0 +- eps)
    out(0).z.doubleValue() should equal(3.8 +- eps)

    out(1).x.doubleValue() should equal(1.2 +- eps)
    out(1).y.doubleValue() should equal(2.7 +- eps)
    out(1).z.doubleValue() should equal(3.9 +- eps)

    out(2).x.doubleValue() should equal(1.3 +- eps)
    out(2).y.doubleValue() should equal(2.6 +- eps)
    out(2).z.doubleValue() should equal(3.0 +- eps)

    out(3).x.doubleValue() should equal(1.4 +- eps)
    out(3).y.doubleValue() should equal(2.5 +- eps)
    out(3).z.doubleValue() should equal(3.2 +- eps)
  }
  test("convert to xyz 0 2 1") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8, 4.4),
      Array(1.2, 2.7, 3.9, 4.4),
      Array(1.3, 2.6, 3.0, 4.4),
      Array(1.4, 2.5, 3.2, 4.5)
    ))
    val out = convertXYZ(array, (0, 2, 1))

    assert(out.size === 4)

    out(0).x.doubleValue() should equal(1.0 +- eps)
    out(0).y.doubleValue() should equal(3.8 +- eps)
    out(0).z.doubleValue() should equal(2.0 +- eps)

    out(1).x.doubleValue() should equal(1.2 +- eps)
    out(1).y.doubleValue() should equal(3.9 +- eps)
    out(1).z.doubleValue() should equal(2.7 +- eps)

    out(2).x.doubleValue() should equal(1.3 +- eps)
    out(2).y.doubleValue() should equal(3.0 +- eps)
    out(2).z.doubleValue() should equal(2.6 +- eps)

    out(3).x.doubleValue() should equal(1.4 +- eps)
    out(3).y.doubleValue() should equal(3.2 +- eps)
    out(3).z.doubleValue() should equal(2.5 +- eps)
  }

}