package vsoc.ga.genetic

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.genetic.impl.{GeneticOps, SelectionStrategies, UtilGa}

import scala.util.Random

class GaTestSuite extends FunSuite with MustMatchers {

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


  class PhenoTesterT extends PhenoTester[String, TestScore] {
    override def test(phenos: Seq[String]): PhenoTesterResult[String, TestScore] = {
      def test(p: String): (TestScore, String) = {
        val r: Double = p.toSeq.map(c => rating(c)).sum
        (TestScore(r, r, r), p)
      }

      val _testedPhenos: Seq[(TestScore, String)] = phenos.map(test)
      val _scores = _testedPhenos.map { case (b, _) => b }
      val _score = UtilGa.meanScore(_scores, TestScoreOps)

      new PhenoTesterResult[String, TestScore] {
        override def testedPhenos: Seq[(TestScore, String)] = _testedPhenos

        override def populationScore: Option[TestScore] = Some(_score)
      }
    }

    override def fullDesc: String = "test"
  }

  class TransformerT extends Transformer[Int, String] {

    override def toPheno(geno: Seq[Int]): String = geno.map(g => intToChar(g)).mkString("")

    override def toGeno(pheno: String): Seq[Int] = pheno.toSeq.map(c => charToInt(c))
  }

  class TestFitnessFunction extends FitnessFunction[TestScore] {

    override def fitness(score: TestScore): Double = score.meanRating

  }

  case class TestScore(
                        minRating: Double,
                        meanRating: Double,
                        maxRating: Double,
                      ) extends Score[TestScore] {

    override def score: Double = meanRating
  }

  object TestScoreOps extends ScoreOps[TestScore] {

    override def sum(score1: TestScore, score2: TestScore): TestScore = TestScore(
      minRating = score1.minRating + score2.minRating,
      meanRating = score1.meanRating + score2.meanRating,
      maxRating = score1.maxRating + score2.maxRating,
    )

    override def div(score: TestScore, divisor: Double): TestScore = TestScore(
      minRating = score.minRating / divisor,
      meanRating = score.meanRating / divisor,
      maxRating = score.maxRating / divisor,
    )

    override def unit: TestScore = TestScore(0, 0, 0)
  }

  case class GaResultTestImpl(
                           score: Option[TestScore],
                           newPopulation: Seq[Seq[Int]]
                         ) extends GaResultTest[TestScore, Int]

  private def popToStr(pop: Seq[Seq[Int]]) = pop.map(p => p.map(x => intToChar(x)).mkString("")).mkString("  ")

  //noinspection ScalaUnusedSymbol
  private def popsToStdout(popStream: Stream[GaResultTest[TestScore, Int]]): Unit =
    for ((pop, n) <- popStream.take(500).zipWithIndex) {
      val popStr = popToStr(pop.newPopulation)
      println(f"$n%4d ${pop.score.get.minRating}%7.1f ${pop.score.get.meanRating}%7.1f ${pop.score.get.maxRating}%7.1f   $popStr")
    }

  def ranAllele(ran: Random): Int = _alleles(ran.nextInt(_alleles.size))

  test("GaTest mutationOnly") {

    val r1 = new Random(987987L)
    val tester: PhenoTesterT = new PhenoTesterT()
    val selStrat: SelectionStrategy[Int] = SelectionStrategies.mutationOnly(0.005, ranAllele, r1)
    val fitFunc = new TestFitnessFunction()
    val trans = new TransformerT
    val gat = new GaTest(tester, selStrat, fitFunc, trans)

    def randomGenome: Seq[Int] = (1 to 10).map(_ => ranAllele(r1))

    val start: GaResultTest[TestScore, Int] = GaResultTestImpl(score = None, newPopulation = for (_ <- 1 to 10) yield randomGenome)

    val popStream = Stream.iterate(start)(r => gat.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }

  test("GaTest crossover") {

    val ran = new Random(987987L)
    val gaTest = new GaTest(
      new PhenoTesterT(),
      SelectionStrategies.crossover(0.005, ranAllele, ran),
      new TestFitnessFunction(),
      new TransformerT)

    def randomGenome: Seq[Int] = (1 to 10).map(_ => ranAllele(ran))

    val start: GaResultTest[TestScore, Int] = GaResultTestImpl(newPopulation = for (_ <- 1 to 10) yield randomGenome, score = None)

    val popStream = Stream.iterate(start)(r => gaTest.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }


  test("GeneticOps crossover 10") {
    val _ran = new Random(987987L)

    val n = 10
    val a = Seq.fill(n)("A")
    val b = Seq.fill(n)("B")
    val r = GeneticOps.crossover(a, b, _ran)

    val (ra, rb) = r.splitAt(8)
    ra mustBe Seq.fill(8)("A")
    rb mustBe Seq.fill(2)("B")
  }

  test("GeneticOps crossover 9") {
    val _ran = new Random(232L)

    val n = 9
    val a = Seq.fill(n)("A")
    val b = Seq.fill(n)("B")
    val r = GeneticOps.crossover(a, b, _ran)

    val (ra, rb) = r.splitAt(1)
    ra mustBe Seq.fill(1)("A")
    rb mustBe Seq.fill(8)("B")
  }

  test("selection strategy crossover") {

    val r = Random.javaRandomToRandom(new java.util.Random(293847L))

    def ra = (_: Random) => 0

    val strat = SelectionStrategies.crossover(0.0, ra, r)

    val tested = for (i <- 0 to 19) yield {
      (i.toDouble, Seq.fill(15)(i))
    }

    val sel: Seq[Seq[Int]] = strat.select(tested)
    val dist = sel.map(s => s.toSet)

    dist.size mustBe tested.size

    dist(0).size mustBe 1
    dist(1).size mustBe 1
    dist(2).size mustBe 1
    dist(3).size mustBe 2
    dist(4).size mustBe 2
    dist(5).size mustBe 2
    dist(6).size mustBe 2
    dist(7).size mustBe 2
    dist(8).size mustBe 2
    dist(9).size mustBe 1
    dist(10).size mustBe 1
    dist(11).size mustBe 1
    dist(12).size mustBe 1
    dist(13).size mustBe 1
    dist(14).size mustBe 1
    dist(15).size mustBe 1
    dist(16).size mustBe 1
    dist(17).size mustBe 1
    dist(18).size mustBe 1
    dist(19).size mustBe 1

    dist(0) must contain(19)
    dist(1) must contain(18)
    dist(2) must contain(17)

    dist(3) must contain(19)
    dist(3) must contain(18)
    dist(4) must contain(19)
    dist(4) must contain(17)
    dist(5) must contain(19)
    dist(5) must contain(16)

    dist(6) must contain(18)
    dist(6) must contain(17)
    dist(7) must contain(18)
    dist(7) must contain(16)

    dist(8) must contain(17)
    dist(8) must contain(16)

    dist(9) must contain(16)
    dist(10) must contain(15)
    dist(11) must contain(14)
    dist(12) must contain(13)
    dist(13) must contain(12)
    dist(14) must contain(11)
    dist(15) must contain(10)
    dist(16) must contain(9)
    dist(17) must contain(8)
    dist(18) must contain(7)
    dist(19) must contain(6)
  }

  test("selection strategy mutation only") {

    val r = Random.javaRandomToRandom(new java.util.Random(293847L))

    def ra = (_: Random) => 111

    val strat = SelectionStrategies.mutationOnly(0.5, ra, r)

    val tested = for (i <- 0 to 7) yield {
      (i.toDouble, Seq.fill(15)(i))
    }

    val sel: Seq[Seq[Int]] = strat.select(tested)
    val dist = sel.map(s => s.distinct.sorted)

    dist.size mustBe tested.size
    dist(0) must contain(7)
    dist(0) must contain(111)
    dist(1) must contain(6)
    dist(1) must contain(111)
    dist(2) must contain(5)
    dist(2) must contain(111)
    dist(3) must contain(4)
    dist(3) must contain(111)
    dist(4) must contain(7)
    dist(4) must contain(111)
    dist(5) must contain(6)
    dist(5) must contain(111)
    dist(6) must contain(5)
    dist(6) must contain(111)
    dist(7) must contain(4)
    dist(7) must contain(111)

  }


}

case class IntScore(value: Int) extends Score[IntScore] {

  override def score: Double = value

}

object IntScoreOps extends ScoreOps[IntScore] {


  override def sum(score1: IntScore, score2: IntScore): IntScore =
    IntScore(score1.value + score2.value)

  override def div(score: IntScore, divisor: Double): IntScore =
    IntScore((score.value / divisor).toInt)

  override def unit: IntScore = IntScore(0)

}

