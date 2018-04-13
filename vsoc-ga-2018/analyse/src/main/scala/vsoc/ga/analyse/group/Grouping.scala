package vsoc.ga.analyse.group

import entelijan.viz.Viz

object Grouping {

  def group(in: Seq[Viz.XY], grpSize: Int): Seq[Viz.XY] = {

    def grp(inGrp: Seq[Viz.XY]): Seq[Viz.XY] = {
      val head = inGrp.head
      val tail = inGrp.tail
      head :: grpTail(tail)
    }

    def grpTail(inTail: Seq[Viz.XY]): List[Viz.XY] = {
      val grps = inTail.grouped(grpSize)
      grps.toList.map(means)
    }

    def means(inGrp: Seq[Viz.XY]): Viz.XY = {
      require(inGrp.nonEmpty)
      val xs = inGrp.map(xy => xy.x.doubleValue()).sum
      val ys = inGrp.map(xy => xy.y.doubleValue()).sum
      Viz.XY(xs / inGrp.size, ys / inGrp.size)
    }

    if (in.size < 3 || grpSize == 1) in
    else grp(in)
  }

}
