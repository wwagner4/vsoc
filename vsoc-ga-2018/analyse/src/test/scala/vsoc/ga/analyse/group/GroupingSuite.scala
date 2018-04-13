package vsoc.ga.analyse.group

import entelijan.viz.Viz.XY
import org.scalatest.{FunSuite, MustMatchers}

class GroupingSuite extends FunSuite with MustMatchers {

  val data = Seq(
    (Seq.empty[XY], Seq.empty[XY], 22),
    (Seq(XY(1, 1)), Seq(XY(1, 1)), 1),
    (Seq(XY(1, 1), XY(1, 3)), Seq(XY(1, 1), XY(1, 3.00001)), 10),
    (Seq(XY(1, 1), XY(1, 3)), Seq(XY(1, 1), XY(1, 3.00001)), 1),

    (
      Seq(
        XY(1, 1),
        XY(1, 3),
        XY(1, 3),
      ),
      Seq(
        XY(1, 1),
        XY(1, 3.00001),
        XY(1, 3),
      ),
      1
    )
  )

  private def mustEqualXY(a: XY, b: XY): Unit = {
    a.x.doubleValue() mustBe (b.x.doubleValue() +- 0.001)
    a.y.doubleValue() must be(b.y.doubleValue() +- 0.001)
  }

  private def mustEqual(a: Seq[XY], b: Seq[XY]): Unit = {
    a.size mustBe b.size
    for (i <- a.indices) {
      mustEqualXY(a(i), b(i))
    }
  }

  for ((in, should, grpSize) <- data) {
    val str = in.mkString("")
    test(s"Grouping $in $grpSize") {
      mustEqual(Grouping.group(in, grpSize), should)
    }
  }

}
