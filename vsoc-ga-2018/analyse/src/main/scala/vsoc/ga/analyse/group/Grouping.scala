package vsoc.ga.analyse.group

import entelijan.viz.Viz

object Grouping {

  def group(in: Seq[Viz.XY], grpSize: Int): Seq[Viz.XY] = {

    def grp(inGrp: Seq[Viz.XY], grpSizeAdj: Int): Seq[Viz.XY] = {
      val head = inGrp.head
      val rtail = inGrp.tail.reverse
      val last = rtail.head
      val tail = rtail.tail.reverse
      head :: grpTail(tail, grpSizeAdj) ::: List(last)
    }

    def grpTail(inTail: Seq[Viz.XY], grpSizeAdj: Int): List[Viz.XY] = {
      val grps = inTail.grouped(grpSizeAdj)
      grps.toList.map(means)
    }

    def means(inGrp: Seq[Viz.XY]): Viz.XY = {
      require(inGrp.nonEmpty)
      val xs = inGrp.map(xy => xy.x.doubleValue()).sum
      val ys = inGrp.map(xy => xy.y.doubleValue()).sum
      Viz.XY(xs / inGrp.size, ys / inGrp.size)
    }

    def adjGrpSize(s: Int, dataSize: Int): Int = {
      require(s >= 1)
      if (s > dataSize) dataSize else s
    }

    if (in.size <= 3 || grpSize == 1) in
    else grp(in, adjGrpSize(grpSize, in.size))
  }

}
