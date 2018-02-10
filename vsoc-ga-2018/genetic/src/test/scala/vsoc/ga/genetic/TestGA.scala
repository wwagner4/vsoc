package vsoc.ga.genetic

import org.scalatest.{FunSuite, MustMatchers}

import scala.util.Random

class TestGA extends FunSuite with MustMatchers {

  private val baseData = Seq(
    ('#', 0, 1.0),
    ('*', 1, 0.7),
    ('+', 2, 0.6),
    ('=', 3, 0.5),
    ('-', 4, 0.4),
    (':', 5, 0.3),
    ('.', 6, 0.2),
    (' ', 7, 0.0),
  )

  private val _alleles = baseData.map(t => t._2)

  private val charToInt: Map[Char, Int] = baseData.map(t => (t._1, t._2)).toMap

  private val intToChar: Map[Int, Char] = baseData.map(t => (t._2, t._1)).toMap

  private val rating: Map[Char, Double] = baseData.map(t => (t._1, t._3)).toMap


  class PhenoTesterT extends PhenoTester[String] {
    override def test(phenos: Seq[String]): Seq[(Double, String)] = {
      def test(p: String): (Double, String) = {
        val r: Double = p.toSeq.map(c => rating(c)).sum
        (r, p)
      }

      phenos.map(test)
    }

  }

  class TransformerT extends Transformer[Int, String] {

    override def toPheno(geno: Seq[Int]): String = geno.map(g => intToChar(g)).mkString("")

    override def toGeno(pheno: String): Seq[Int] = pheno.toSeq.map(c => charToInt(c))
  }

  case class Score(
                    minRating: Double,
                    meanRating: Double,
                    maxRating: Double,
                  )

  case class GAResultImpl(
                           score: Option[Score],
                           newPopulation: Seq[Seq[Int]]
                         ) extends GAResult[Int, Score]

  private def popToStr(pop: Seq[Seq[Int]]) = pop.map(p => p.map(x => intToChar(x)).mkString("")).mkString("  ")

  private def popsToStdout(popStream: Stream[GAResult[Int, Score]]): Unit =
    for ((pop, n) <- popStream.take(500).zipWithIndex) {
      val popStr = popToStr(pop.newPopulation)
      println(f"$n%4d ${pop.score.get.minRating}%7.1f ${pop.score.get.meanRating}%7.1f ${pop.score.get.maxRating}%7.1f   $popStr")
    }

  def ranAllele(ran: Random): Int = _alleles(ran.nextInt(_alleles.size))

  class GaScore[A, P](
                                tester: PhenoTester[P],
                                selStrat: SelectionStrategy[A],
                                transformer: Transformer[A, P]
                              )
    extends GA[A, P, Score](
      tester,
      selStrat,
      transformer,
      classOf[Score]
    ) {

    /**
      * Score independent from phenotype.
      * This implementation could be used for all GAs
      */
    override def createScore(testedPhenos: Seq[(Double, P)]): Option[Score] = {
      val ratings = testedPhenos.map(t => t._1)
      val (max, mean, min) = UtilGa.minMeanMax(ratings)
      Some(Score(max, mean, min))
    }
  }

  test("GA mutationOnly") {

    val r1 = new Random(987987L)
    val gat = new GaScore(new PhenoTesterT(), SelectionStrategies.mutationOnly(0.005, ranAllele, r1), new TransformerT)

    def randomGenome: Seq[Int] = (1 to 10).map(_ => ranAllele(r1))

    val start: GAResult[Int, Score] = GAResultImpl(newPopulation = for (_ <- 1 to 10) yield randomGenome, score = None)

    val popStream = Stream.iterate(start)(r => gat.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }

  test("GA crossover") {

    val ran = new Random(987987L)
    val gaTest = new GaScore(new PhenoTesterT(), SelectionStrategies.crossover(0.005, ranAllele, ran), new TransformerT)

    def randomGenome: Seq[Int] = (1 to 10).map(_ => ranAllele(ran))

    val start: GAResult[Int, Score] = GAResultImpl(newPopulation = for (_ <- 1 to 10) yield randomGenome, score = None)

    val popStream = Stream.iterate(start)(r => gaTest.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }

}

