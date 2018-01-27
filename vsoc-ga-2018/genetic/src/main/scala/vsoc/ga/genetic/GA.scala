package vsoc.ga.genetic

class GA[A, P](
                val tester: PhenoTester[P],
                val selStrat: SelectionStrategy[A],
                val transformer: Transformer[A, P]
              ) {

  def nextPopulation(pop: Seq[Seq[A]]): GAResult[A] = {
    def minMeanMax(values: Seq[Double]): (Double, Double, Double) = {
      val sorted = values.sorted
      val sum = values.sum
      (sorted.head, sum / values.size, sorted.last)
    }

    val popSize = pop.size
    val phenos: Seq[P] = pop.map(transformer.toPheno)
    val testedPhenos: Seq[(Double, P)] = tester.test(phenos)
    val testedGenos: Seq[(Double, Seq[A])] = testedPhenos.map { case (r, g) => (r, transformer.toGeno(g)) }
    val newPop = selStrat.select(popSize, testedGenos)
    val (min, mean, max) = minMeanMax(testedGenos.map(t => t._1))
    new GAResult[A] {
      override def minRating: Double = min

      override def meanRating: Double = mean

      override def maxRating: Double = max

      override def newPopulation: Seq[Seq[A]] = newPop

    }
  }
}

trait GAResult[A] {
  def minRating: Double

  def meanRating: Double

  def maxRating: Double

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