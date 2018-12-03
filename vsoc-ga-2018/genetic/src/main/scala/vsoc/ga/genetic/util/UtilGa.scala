package vsoc.ga.genetic.util

import vsoc.ga.genetic._

object UtilGa {

  def minMeanMax(values: Seq[Double]): (Double, Double, Double) = {
    val sorted = values.sorted
    val sum = values.sum
    (sorted.head, sum / values.size, sorted.last)
  }

  def  meanScore[S <: Score[S]](s: Seq[S], ops: ScoreOps[S]): S = {
    if (s.isEmpty) ops.unit
    else {
      val len = s.size
      val sum = s.reduce(ops.sum)
      ops.div(sum, len)
    }
  }

}
