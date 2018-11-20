package vsoc.ga.genetic

import scala.util.Random

trait GeneticOps[A] {

  def ran: Random

  def crossover(a: Geno[A], b: Geno[A]): Geno[A] = {
    require(a.genos.size == b.genos.size, "Sizes of gnomes must be the same")
    val cutPoint = ran.nextInt(a.genos.size)

    val (a1, _) = a.genos.splitAt(cutPoint)
    val (_, b1) = b.genos.splitAt(cutPoint)
    Geno(a1 ++ b1)
  }

  def mutation(a: Geno[A], mutationRate: Double, ranAllele: Random => A): Geno[A] = {
    val genos = a.genos.map { a =>
      val mut = ran.nextDouble() < mutationRate
      if (mut) ranAllele(ran) else a
    }
    Geno(genos)
  }

}

