package vsoc.ga.trainga.thinner

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.trainga.thinner.impl.ThinnerIndex

class ThinnerSuite extends FunSuite with MustMatchers {

  test("thinner 20") {
    val t = ThinnerIndex.thin(1 to 20)
    t must contain(20)
    t must contain(19)
    t must contain(18)
    t must contain(17)
    t must contain(16)

    t must contain(10)
    t must contain(1)
    t.distinct.size mustBe 7
  }

  test("thinner 1 19 20") {
    val t = ThinnerIndex.thin(Seq(1, 19, 20))
    t must contain(20)
    t must contain(19)
    t must contain(1)
    t.distinct.size mustBe 3
  }

  test("thinner 1 19 15 20") {
    val t = ThinnerIndex.thin(Seq(1, 19, 15, 20))
    t must contain(20)
    t must contain(19)
    t must contain(1)
    t.distinct.size mustBe 3
  }

  test("thinner 0 20") {
    val t = ThinnerIndex.thin(0 to 20)
    t must contain(20)
    t must contain(19)
    t must contain(18)
    t must contain(17)
    t must contain(16)

    t must contain(10)
    t must contain(0)
    t.distinct.size mustBe 7
  }

  test("thinner 123") {
    val t = ThinnerIndex.thin(1 to 123)
    t must contain(123)
    t must contain(122)
    t must contain(121)
    t must contain(120)
    t must contain(119)

    t must contain(110)
    t must contain(100)
    t must contain(90)
    t must contain(80)
    t must contain(70)

    t must contain(1)
    t.distinct.size mustBe 11
  }

  test("thinner 4000") {
    val t = ThinnerIndex.thin(1 to 4000)
    t must contain(4000)
    t must contain(3999)
    t must contain(3998)
    t must contain(3997)
    t must contain(3996)

    t must contain(3990)
    t must contain(3980)
    t must contain(3970)
    t must contain(3960)
    t must contain(3950)

    t must contain(3900)
    t must contain(3800)
    t must contain(3700)
    t must contain(3600)
    t must contain(3500)

    t must contain(3000)
    t must contain(2000)
    t must contain(1000)
    t must contain(1)

    t.distinct.size mustBe 19
  }

}
