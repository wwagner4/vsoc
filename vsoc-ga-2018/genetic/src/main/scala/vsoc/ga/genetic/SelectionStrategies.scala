package vsoc.ga.genetic

import scala.util.Random

object SelectionStrategies {

  /**
    * Selection strategy that uses mutation and crossover
    * @param mutationRate A value between 0.0 and 1.0
    * @param randomAllele A function that returns a random allele
    * @param _ran A random generator from outside in order to create reproducible results
    * @tparam A Class of alleles
    */
  def crossover[A](mutationRate: Double, randomAllele: Random => A, _ran: Random): SelectionStrategy[A] = {
    new SelectionStrategy[A] with GeneticOps[A] {

      def minSize = 3

      override val ran: Random = _ran

      override def select(popSize: Int, tested: Seq[(Double, Seq[A])]): Seq[Seq[A]] = {
        require(tested.length >= minSize, s"population size ${tested.size} too small. required $minSize")
        val sorted = tested.sortBy(f => -f._1).map(t => t._2).toList
        val cos = List(
          crossover(sorted(0), sorted(1)),
          crossover(sorted(0), sorted(2)),
          crossover(sorted(0), sorted(3)),
          crossover(sorted(1), sorted(2)),
          crossover(sorted(1), sorted(3)),
          crossover(sorted(2), sorted(3))
        )
        val all: Seq[Seq[A]] = (sorted.take(3) ::: cos ::: sorted.drop(3)).take(popSize)
        all.map(g => mutation(g, mutationRate, randomAllele))
      }

    }
  }

  /**
    * Selection strategy that uses only mutation and no crossover
    * @param mutationRate A value between 0.0 and 1.0
    * @param randomAllele A function that returns a random allele
    * @param _ran A random generator from outside in order to create reproducible results
    * @tparam A Class of alleles
    */
  def mutationOnly[A](mutationRate: Double, randomAllele: Random => A, _ran: Random): SelectionStrategy[A] = {
    new SelectionStrategy[A] with GeneticOps[A] {

      def minSize = 4

      override def ran: Random = _ran

      override def select(popSize: Int, tested: Seq[(Double, Seq[A])]): Seq[Seq[A]] = {
        require(tested.size >= minSize, s"population size ${tested.size} too small. required $minSize")
        val sorted = tested.sortBy(f => -f._1).map(t => t._2).toList
        val all = (List(
          sorted(0),
          sorted(1),
          sorted(2),
          sorted(3)
        ) ::: sorted).take(popSize)
        all.map(t => mutation(t, mutationRate, randomAllele))
      }
    }
  }
}