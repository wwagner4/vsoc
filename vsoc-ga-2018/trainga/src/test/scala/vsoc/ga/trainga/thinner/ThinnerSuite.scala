package vsoc.ga.trainga.thinner

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.trainga.thinner.impl.ThinnerIndex

class ThinnerSuite extends FunSuite with MustMatchers {

  test("thinner 20") {
    val t = ThinnerIndex.thin(0 to 20)
    t must contain(20)
    t must contain(0)
    t.distinct.size mustBe 10
  }

  test("thinner 123") {
    val t = ThinnerIndex.thin(0 to 123)
    t must contain(123)
    t must contain(0)
    t.distinct.size mustBe 10
  }

  test("thinner 4000") {
    val t = ThinnerIndex.thin(0 to 4000)
    t must contain(4000)
    t must contain(0)
    t.distinct.size mustBe 10
  }

}
