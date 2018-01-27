package vsoc.ga.genetic

import scala.util.Random

trait GeneticOps[A] {

  def ran: Random

  def crossover(a: Seq[A], b: Seq[A]): Seq[A] = {
    require(a.size == b.size, "Sizes of gnomes must be the same")
    val cutPoint = ran.nextInt(a.size)

    def co(al: List[A], bl: List[A], i: Int): List[A] = {
      if (al.isEmpty) Nil
      else if (i % 2 == 0 && i < cutPoint) al.head :: co(al.tail, bl.tail, i + 1)
      else if (i % 2 == 1 && i <= cutPoint) al.head :: co(al.tail, bl.tail, i + 1)
      else bl.head :: co(al.tail, bl.tail, i + 1)
    }

    co(a.toList, b.toList, 0)
  }

  def mutation(a: Seq[A], mutationRate: Double, ranAllele: Random => A): Seq[A] = {
    a.map { a =>
      val mut = ran.nextDouble() < mutationRate
      if (mut) ranAllele(ran) else a
    }

  }

}

