package vsoc.ga.trainga.ga

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.common.data.Data02

class FitnessFunctionsSuite extends FunSuite with MustMatchers {

  test("data02A one kick") {
    val d = Data02(kicksMax = 1)
    val s = FitnessFunctions.data02A01.fitness(d)
    s mustBe(1.0 +- 0.001)
  }

  test("data02A one kick two kick min") {
    val d = Data02(kicksMax = 1, kicksMin = 2)
    val s = FitnessFunctions.data02A01.fitness(d)
    s mustBe(21.0 +- 0.001)
  }

}

