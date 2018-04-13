package vsoc.ga.analyse.group

import entelijan.viz.Viz

object Grouping {

  def group(in: Seq[Viz.XY], grpSize: Int): Seq[Viz.XY] = {

    def doGroup(in: Seq[Viz.XY]): Seq[Viz.XY] = {
      val head = in.head
      val tail = in.tail
      head :: doGroup1(tail)
    }

    def doGroup1(in: Seq[Viz.XY]): List[Viz.XY] = {
      val grps: List[Seq[Viz.XY]] = in.grouped(grpSize).toList
      grps.map(grp => doGroup2(grp))
    }

    def doGroup2(in: Seq[Viz.XY]): Viz.XY = {
      require(in.nonEmpty)
      val xs = in.map(xy => xy.x.doubleValue()).sum
      val ys = in.map(xy => xy.y.doubleValue()).sum
      Viz.XY(xs / in.size, ys / in.size)
    }

    if (in.size < 3 || grpSize == 1) in
    else doGroup(in)
  }

}
