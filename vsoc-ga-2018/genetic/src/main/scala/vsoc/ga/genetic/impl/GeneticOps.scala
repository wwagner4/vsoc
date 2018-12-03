package vsoc.ga.genetic.impl

import scala.util.Random

object GeneticOps {

  def crossover[A](a: Seq[A], b: Seq[A], ran: Random): Seq[A] = {
    require(a.size == b.size, "Sizes of gnomes must be the same")
    val cutPoint = ran.nextInt(a.size)

    val (a1, _) = a.splitAt(cutPoint)
    val (_, b1) = b.splitAt(cutPoint)
    a1 ++ b1
  }

  def mutation[A](a: Seq[A], mutationRate: Double, ranAllele: Random => A, ran: Random): Seq[A] = {
    a.map { a =>
      val mut = ran.nextDouble() < mutationRate
      if (mut) ranAllele(ran) else a
    }

  }

}
