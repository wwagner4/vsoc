package vsoc.ga.trainga.ga.common

import org.scalatest.{FunSuite, MustMatchers}

class ValuesByIndexCollectorSuite extends FunSuite with MustMatchers {

  test("Added no value") {
    val tc = new TC()
    tc.sum(0) mustBe 0
    tc.mean(0) mustBe 0
  }

  test("Added one value") {
    val tc = new TC()
    tc.putValue(0, 1)
    tc.sum(0) mustBe 1
    tc.mean(0) mustBe 1
  }

  test("Added two values") {
    val tc = new TC()
    tc.putValue(0, 1)
    tc.putValue(0, 3)
    tc.sum(0) mustBe 4.0 +- 0.0001
    tc.mean(0) mustBe 2.0 +- 0.0001
  }

  test("Added values on different indexes") {
    val tc = new TC()
    tc.putValue(0, 1)
    tc.putValue(2, 3)
    tc.sum(0) mustBe 1.0 +- 0.0001
    tc.mean(0) mustBe 1.0 +- 0.0001
    tc.sum(1) mustBe 0.0 +- 0.0001
    tc.mean(1) mustBe 0.0 +- 0.0001
    tc.sum(2) mustBe 3.0 +- 0.0001
    tc.mean(2) mustBe 3.0 +- 0.0001
  }

  test("More than two values") {
    val tc = new TC()
    tc.putValue(0, 1)
    tc.putValue(0, 2)
    tc.putValue(0, 3)
    tc.putValue(0, 4)
    tc.putValue(0, 5)
    tc.sum(0) mustBe 15.0 +- 0.0001
    tc.mean(0) mustBe 3.0 +- 0.0001
  }

  test("Added unit value") {
    val tc = new TC()
    tc.putValue(0, 0)
    tc.sum(0) mustBe 0
    tc.mean(0) mustBe 0
  }

}

