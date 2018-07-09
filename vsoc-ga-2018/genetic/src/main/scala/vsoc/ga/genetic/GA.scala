package vsoc.ga.genetic

import vsoc.ga.common.describe.Describable

/**
  * A complete genetic algorithm for arbitrary pheno- and genotype
  * Phenotype: Any Class that can be somehow tested for fitness
  * Genotype: Sequence of alleles. Alleles might be any type that allows
  * the creation of a phenotype out of this sequence and that
  * can be created out of a phenotype.
  *
  * For an example see TestGA
  *
  * @param tester      Tests the fitness of phenotypes. Might also create a populationScore
  * @param selStrategy Selects a new population out of a sequence of rated
  *                    genotypes. Might (should) apply mutation and crossover
  *                    to the members of the newly generated population.
  * @param transformer Transforms geno- to phenotype and vice versa.
  * @tparam A Class of an allele
  * @tparam P Class of the phenotype
  * @tparam S Class of the populationScore. The populationScore might give you insight into the process of creating the
  *           next generation. It can help to decide if generating more generations makes sense
  */
class GA[A, P, S](
                   val tester: PhenoTester[P, S],
                   val selStrategy: SelectionStrategy[A],
                   val transformer: Transformer[A, P],
                 ) {

  def nextPopulation(pop: Seq[Seq[A]]): GAResult[S, A] = {

    val phenos: Seq[P] = pop.map(transformer.toPheno)
    val testResult = tester.test(phenos)
    val testedGenos: Seq[(Double, Seq[A])] = testResult.testedPhenos.map { case (r, g) => (r, transformer.toGeno(g)) }
    val newPop = selStrategy.select(testedGenos)
    new GAResult[S, A] {

      def score: Option[S] = testResult.populationScore

      override def newPopulation: Seq[Seq[A]] = newPop

    }
  }
}

/**
  * Result of testing one the result of testing a population
  * @tparam S Score data. Some data containing the fitness value of the population and some additional parameters
  *           that where used to calculate that fitness value.
  * @tparam A Type of one parameter genotype parameter. Usually a Double
  */
trait GAResult[S, A] {

  def score: Option[S]

  def newPopulation: Seq[Seq[A]]
}


trait PhenoTesterResult[P, S] {

  /**
    * @return Sequence of Phenotypes with their score value
    */
  def testedPhenos: Seq[(Double, P)]

  /**
    * @return The mean score of the population
    */
  def populationScore: Option[S]
}

trait PhenoTester[P, S] extends Describable {
  def test(phenos: Seq[P]): PhenoTesterResult[P, S]
}

trait SelectionStrategy[A] extends Describable {
  def select(tested: Seq[(Double, Seq[A])]): Seq[Seq[A]]
}

trait Transformer[A, P] {

  def toPheno(geno: Seq[A]): P

  def toGeno(pheno: P): Seq[A]
}