package vsoc.ga.matches.nn.impl

import org.scalatest.{FunSuite, MustMatchers}

class ValuesHistoryTest extends FunSuite with MustMatchers {

  test("dimension [2,1]") {
    val vh = new ValuesHistory(2,1)
    val d1 = vh.data
    d1.length mustBe 2

    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(1).length mustBe 1

    d1(0)(0) mustBe 0.00 +- 0.0001
    d1(1)(0) mustBe 0.00 +- 0.0001
  }

  test("dimension [2,1] add one value") {
    val vh = new ValuesHistory(2,1)
    vh.addData(Array(1.0))
    val d1 = vh.data

    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(1).length mustBe 1

    d1(0)(0) mustBe 1.00 +- 0.0001
    d1(1)(0) mustBe 0.00 +- 0.0001
  }

  test("dimension [2,1] add two values") {
    val vh = new ValuesHistory(2,1)
    vh.addData(Array(1.0))
    vh.addData(Array(2.0))
    val d1 = vh.data

    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(1).length mustBe 1

    d1(0)(0) mustBe 2.00 +- 0.0001
    d1(1)(0) mustBe 1.00 +- 0.0001
  }

  test("dimension [2,1] add three values") {
    val vh = new ValuesHistory(2,1)
    vh.addData(Array(1.0))
    vh.addData(Array(2.0))
    vh.addData(Array(3.0))
    val d1 = vh.data

    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(1).length mustBe 1

    d1(0)(0) mustBe 3.00 +- 0.0001
    d1(1)(0) mustBe 2.00 +- 0.0001
  }

  test("dimension [2,1] add four values") {
    val vh = new ValuesHistory(2,1)
    vh.addData(Array(0.1))
    vh.addData(Array(0.1))
    vh.addData(Array(0.2))
    vh.addData(Array(-0.3))
    val d1 = vh.data

    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(1).length mustBe 1

    d1(0)(0) mustBe -0.3 +- 0.0001
    d1(1)(0) mustBe 0.2 +- 0.0001
  }

  test("dimension [3,4] add two values") {
    val vh = new ValuesHistory(3,4)
    vh.addData(Array(0.1, 0.2, 0.1, 0.2))
    vh.addData(Array(0.2, 0.1, 0.2, 0.1))
    val d1 = vh.data

    d1.length mustBe 3
    d1(0).length mustBe 4
    d1(1).length mustBe 4
    d1(2).length mustBe 4

    d1(0)(0) mustBe 0.2 +- 0.0001
    d1(1)(0) mustBe 0.1 +- 0.0001
    d1(2)(0) mustBe 0.0 +- 0.0001
  }

  test("dimension [3,4] add four values") {
    val vh = new ValuesHistory(3,4)
    vh.addData(Array(0.1, 0.2, 0.1, 0.2))
    vh.addData(Array(0.2, 0.1, 0.2, 0.2))
    vh.addData(Array(0.3, 0.3, 0.1, 0.3))
    vh.addData(Array(0.4, 0.3, 0.2, 0.3))
    val d1 = vh.data

    d1.length mustBe 3
    d1(0).length mustBe 4
    d1(1).length mustBe 4
    d1(2).length mustBe 4

    d1(0)(0) mustBe 0.4 +- 0.0001
    d1(1)(0) mustBe 0.3 +- 0.0001
    d1(2)(0) mustBe 0.2 +- 0.0001

    d1(0)(1) mustBe 0.3 +- 0.0001
    d1(1)(1) mustBe 0.3 +- 0.0001
    d1(2)(1) mustBe 0.1 +- 0.0001

    d1(0)(2) mustBe 0.2 +- 0.0001
    d1(1)(2) mustBe 0.1 +- 0.0001
    d1(2)(2) mustBe 0.2 +- 0.0001

    d1(0)(3) mustBe 0.3 +- 0.0001
    d1(1)(3) mustBe 0.3 +- 0.0001
    d1(2)(3) mustBe 0.2 +- 0.0001

  }

}
