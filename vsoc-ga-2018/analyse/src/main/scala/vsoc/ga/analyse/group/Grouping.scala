package vsoc.ga.analyse.group

import entelijan.viz.Viz

object Grouping {

  def group(in: Seq[Viz.XY], grpSize: Int): Seq[Viz.XY] = {
    if (in.size < 3 || grpSize == 1) in
    else doGroup(in)
  }

  private def doGroup(in: Seq[Viz.XY]): Seq[Viz.XY] = {
    ???
  }

}
