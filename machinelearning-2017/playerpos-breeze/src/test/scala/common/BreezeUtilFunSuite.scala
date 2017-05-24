package common

import org.scalatest.FunSuite
import breeze.linalg._

class BreezeUtilFunSuite extends FunSuite {

  test("slice row") {
    val m = DenseMatrix(
      (1.0, 2.0, 3.0, 4.0),
      (2.0, 3.0, 4.0, 5.0),
      (3.0, 4.0, 5.0, 6.0)
    )
    val row = BreezeUtil.sliceRow(m, 1)

    assert(row.cols === 4)
    assert(row.rows === 1)
    assert(row(0, 0) === 2.0)
    assert(row(0, 1) === 3.0)
    assert(row(0, 2) === 4.0)
    assert(row(0, 3) === 5.0)
  }


}
