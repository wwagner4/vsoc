package vsoc.ga.trainga.thinner.impl

object ThinnerIndex {

  def thin(currentIndexes: Seq[Int]): Seq[Int] = {
    val max = currentIndexes.max
    val min = currentIndexes.min
    val keep = idxToBeKept(max, min, 1)
    currentIndexes.filter(i => keep.contains(i))
  }

  def nextMax(max: Int, step: Int): Int = {
    if (max % step == 0) max
    else nextMax(max - 1, step)
  }

  def idxToBeKept(max: Int, min:Int,step: Int): Seq[Int] = {
    val max1 = nextMax(max, step)
    val idx = Stream.iterate(max1)(i => i - step).take(5).filter(_ >= 0)
    val min1 = idx.min
    if (min1 <= min) Seq(min) ++ idx
    else idx ++ idxToBeKept(min1, min, step * 10)
  }
}
