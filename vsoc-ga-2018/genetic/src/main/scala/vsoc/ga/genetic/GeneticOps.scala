package vsoc.ga.genetic

import scala.util.Random

trait GeneticOps[A] {

  def ran: Random

  def crossover(a: Geno[A], b: Geno[A]): Geno[A] = {
    require(a.alleles.size == b.alleles.size, "Sizes of gnomes must be the same")
    val cutPoint = ran.nextInt(a.alleles.size)

    val (a1, _) = a.alleles.splitAt(cutPoint)
    val (_, b1) = b.alleles.splitAt(cutPoint)
    Geno(a1 ++ b1)
  }

  def mutation(a: Geno[A], mutationRate: Double, ranAllele: Random => A): Geno[A] = {
    val genos = a.alleles.map { a =>
      val mut = ran.nextDouble() < mutationRate
      if (mut) ranAllele(ran) else a
    }
    Geno(genos)
  }

}

