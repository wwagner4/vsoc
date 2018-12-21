package vsoc.ga.common.viz

import entelijan.viz.Viz

/**
  * Converts a time series of data into another series where groups of values are
  * treated as one value.
  *
  * Should be moved to the viz
  */
object Smoothing {

  def smooth(in: Seq[Viz.XY], grpSize: Int): Seq[Viz.XY] = {

    def smoothInternal(inGrp: Seq[Viz.XY], grpSizeAdj: Int): Seq[Viz.XY] = {
      val head = inGrp.head
      val rtail = inGrp.tail.reverse
      val last = rtail.head
      val tail = rtail.tail.reverse
      head :: smoothTail(tail, grpSizeAdj) ::: List(last)
    }

    def smoothTail(inTail: Seq[Viz.XY], grpSizeAdj: Int): List[Viz.XY] = {
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
    else smoothInternal(in, adjGrpSize(grpSize, in.size))
  }

}
