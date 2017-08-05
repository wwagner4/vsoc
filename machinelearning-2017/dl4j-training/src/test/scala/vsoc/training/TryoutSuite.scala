package vsoc.training

import org.scalatest.{FunSuite, Matchers}

class TryoutSuite extends FunSuite with Matchers {

  import vsoc.common.Formatter._

  test("format double") {
    val d = 0.001
    val str = formatNumber("%.6f", d)

    str should be ("0.001000")

  }

}
