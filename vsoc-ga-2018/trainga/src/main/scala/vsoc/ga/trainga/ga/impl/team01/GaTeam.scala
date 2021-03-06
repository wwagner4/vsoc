package vsoc.ga.trainga.ga.impl.team01

import vsoc.ga.genetic._

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
class GaTeam[A, P <: Pheno[A], S](
                                               val tester: PhenoTester[P, A, S],
                                               val selStrategy: SelectionStrategy[A],
                                               val fitnessFunction: FitnessFunction[S],
                                               val transformer: Transformer[A, P],
                                               val meanScore: (Seq[S] => S)
                                             ) {

  def nextPopulation(pop: Seq[Seq[A]]): GaReturnTeam[S, A] = {

    val phenos: Seq[P] = pop.map(transformer.toPheno)
    val testResult: Seq[(S, P)] = tester.test(phenos)
    val testedGenos: Seq[(Double, Seq[A])] =
      testResult.map {
        case (r, g) => (
          fitnessFunction.fitness(r),
          g.geno)
      }

    val scores = testResult.map{case (s, _) => s}
    val mscore: S = meanScore(scores)

    val newPop = selStrategy.select(testedGenos)
    new GaReturnTeam[S, A] {

      def score: Option[S] = Some(mscore)

      override def newPopulation: Seq[Seq[A]] = newPop

    }
  }


}
