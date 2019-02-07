package vsoc.ga.matches.nn.impl

import org.scalatest.{FunSuite, MustMatchers}

class ValuesHistoryTest extends FunSuite with MustMatchers {

  test("dimension [2,1]") {
    val vh = new ValuesHistory(2,1)
    val d1 = vh.data
    d1.length mustBe 2
    d1(0).length mustBe 1
    d1(0)(0) mustBe 0.00 +- 0.0001
  }

}
