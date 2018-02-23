package vsoc.ga.genetic

import scala.util.Random

trait GeneticOps[A] {

  def ran: Random

  def crossover(a: Seq[A], b: Seq[A]): Seq[A] = {
    require(a.size == b.size, "Sizes of gnomes must be the same")
    val cutPoint = ran.nextInt(a.size)

    val (a1, _) = a.splitAt(cutPoint)
    val (_, b1) = b.splitAt(cutPoint)
    a1 ++ b1
  }

  def mutation(a: Seq[A], mutationRate: Double, ranAllele: Random => A): Seq[A] = {
    a.map { a =>
      val mut = ran.nextDouble() < mutationRate
      if (mut) ranAllele(ran) else a
    }

  }

}

