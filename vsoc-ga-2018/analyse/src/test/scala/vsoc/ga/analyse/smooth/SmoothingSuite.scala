package vsoc.ga.analyse.smooth

import entelijan.viz.Viz.XY
import org.scalatest.{FunSuite, MustMatchers}

class SmoothingSuite extends FunSuite with MustMatchers {

  val data = Seq(
    (Seq.empty[XY], Seq.empty[XY], 22),
    (Seq(XY(1, 1)), Seq(XY(1, 1)), 1),
    (Seq(XY(1, 1), XY(1, 3)), Seq(XY(1, 1), XY(1, 3.0000001)), 10),
    (Seq(XY(1, 1), XY(1, 3)), Seq(XY(1, 1), XY(1, 3.00001)), 1),

    (
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 3),
      ),
      Seq(
        XY(1, 1),
        XY(2, 3.00001),
        XY(3, 3),
      ),
      1
    ),

    (
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 4),
      ),
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 4),
      ),
      2
    ),
    (
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 4),
        XY(4, 6),
        XY(5, 4),
      ),
      Seq(
        XY(1, 1),
        XY(2.5, 3.5),
        XY(4, 6),
        XY(5, 4),
      ),
      2
    ),
    (
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 4),
        XY(4, 6),
        XY(5, 4),
        XY(6, 1),
      ),
      Seq(
        XY(1, 1),
        XY(2.5, 3.5),
        XY(4.5, 5.0),
        XY(6, 1.0),
      ),
      2
    ),
    (
      Seq(
        XY(1, 1),
        XY(2, 3),
        XY(3, 4),
        XY(4, 6),
        XY(5, 4),
      ),
      Seq(
        XY(1, 1),
        XY(3, 4.3333),
        XY(5, 4),
      ),
      20
    ),
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
      mustEqual(Smoothing.smooth(in, grpSize), should)
    }
  }

}
