package vsoc.ga.matches.nn.impl

import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.util.ArrayUtil
import org.scalatest.{FunSuite, MustMatchers}

class NdArrayRankTest extends FunSuite with MustMatchers {

  test("[3,3] random values") {
    val d = Array(
      Array(3.2, 2.0, 4.0),
      Array(-1.0, 1.0, 2.0),
      Array(-1.0, 1.0, 2.0),
    )

    val m = Nd4j.create(ArrayUtil.flatten(d), Array(2,3,1))

    println("shape:" + m.shape().toList)
    println("stride:" + m.stride().toList)


    m.rank() mustBe 3

  }


}
