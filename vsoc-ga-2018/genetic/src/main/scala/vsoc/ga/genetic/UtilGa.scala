package vsoc.ga.genetic

object UtilGa {

  def minMeanMax(values: Seq[Double]): (Double, Double, Double) = {
    val sorted = values.sorted
    val sum = values.sum
    (sorted.head, sum / values.size, sorted.last)
  }

}
