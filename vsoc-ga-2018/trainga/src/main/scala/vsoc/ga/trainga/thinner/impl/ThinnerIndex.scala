package vsoc.ga.trainga.thinner.impl

import scala.util.Random

object ThinnerIndex {

  def thin(currentIndexes: Seq[Int]): Seq[Int] = {
    val maxIndex = currentIndexes.max
    val minIndex = currentIndexes.min
    val inner1 = currentIndexes.filter(e => e != maxIndex && e != minIndex)
    val inner = Random.shuffle(inner1).take(8)
    Seq(minIndex) ++ inner ++ Seq(maxIndex)
  }
}
