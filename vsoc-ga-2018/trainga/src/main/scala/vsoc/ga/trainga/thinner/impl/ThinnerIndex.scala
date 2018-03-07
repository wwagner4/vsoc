package vsoc.ga.trainga.thinner.impl

object ThinnerIndex {

  def thin(currentIndexes: Seq[Int]): Seq[Int] = {
    val max = currentIndexes.max
    val keep = idxToBeKept(max, 1)
    currentIndexes.filter(i => keep.contains(i))
  }

  def nextMax(max: Int, step: Int): Int = {
    if (max % step == 0) max
    else nextMax(max - 1, step)
  }

  def idxToBeKept(max: Int, step: Int): Seq[Int] = {
    val max1 = nextMax(max, step)
    val idx = Stream.iterate(max1)(i => i - step).take(5).filter(_ >= 0)
    val min1 = idx.min
    if (min1 <= 0) idx
    else idx ++ idxToBeKept(min1, step * 10)
  }
}
