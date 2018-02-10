package vsoc.ga.genetic

/**
  * A complete genetic algorithm for arbitrary pheno- and genotype
  * Phenotype: Any Class that can be somehow tested for fitness
  * Genotype: Sequence of alleles. Alleles might be any type that allows
  *           the creation of a phenotype out of this sequence and that
  *           can be created out of a phenotype.
  *
  * For an example see TestGA
  *
  * @param tester Tests the fitness of phenotypes
  * @param selStrat Selects a new population out of a sequence of rated
  *                 genotypes. Might (should) apply mutation and crossover
  *                 to the members of the newly generated population.
  * @param transformer Transforms geno- to phenotype and vice versa.
  * @tparam A Class of an allele
  * @tparam P Class of the phenotype
  * @tparam S Class of the score. The score might give you insight into the process of creating the
  *           next generation. It can help to decide if generating more generations makes sense
  */
class GA[A, P, S](
                val tester: PhenoTester[P],
                val selStrat: SelectionStrategy[A],
                val transformer: Transformer[A, P],
                val scoreClass: Class[S],
              ) {

  def createScore(testedPhenos: Seq[(Double, P)]): Option[S] = None

  def nextPopulation(pop: Seq[Seq[A]]): GAResult[A, S] = {

    val popSize = pop.size
    val phenos: Seq[P] = pop.map(transformer.toPheno)
    val testedPhenos: Seq[(Double, P)] = tester.test(phenos)
    val testedGenos: Seq[(Double, Seq[A])] = testedPhenos.map { case (r, g) => (r, transformer.toGeno(g)) }
    val newPop = selStrat.select(popSize, testedGenos)
    new GAResult[A, S] {

      def score: Option[S] = createScore(testedPhenos)

      override def newPopulation: Seq[Seq[A]] = newPop

    }
  }
}

trait GAResult[A, S] {

  def score: Option[S]

  def newPopulation: Seq[Seq[A]]
}

trait PhenoTester[P] {
  def test(phenos: Seq[P]): Seq[(Double, P)]
}

trait SelectionStrategy[A] {
  def select(popSize: Int, tested: Seq[(Double, Seq[A])]): Seq[Seq[A]]
}

trait Transformer[A, P] {

  def toPheno(geno: Seq[A]): P

  def toGeno(pheno: P): Seq[A]
}