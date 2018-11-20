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


  class PhenoTesterT extends PhenoTester[String, Score] {
    override def test(phenos: Seq[String]): PhenoTesterResult[String, Score] = {
      def test(p: String): (Double, String) = {
        val r: Double = p.toSeq.map(c => rating(c)).sum
        (r, p)
      }

      def calculateScore(testedPhenos: Seq[(Double, String)]): Score = {
        val ratings = testedPhenos.map(t => t._1)
        val (max, mean, min) = UtilGa.minMeanMax(ratings)
        Score(max, mean, min)
      }

      val _testedPhenos = phenos.map(test)
      val _score = calculateScore(_testedPhenos)

      new PhenoTesterResult[String, Score] {
        override def testedPhenos: Seq[(Double, String)] = _testedPhenos

        override def populationScore: Option[Score] = Some(_score)
      }
    }

    override def fullDesc: String = "test"
  }

  class TransformerT extends Transformer[Int, String] {

    override def toPheno(geno: Geno[Int]): String = geno.genos.map(g => intToChar(g)).mkString("")

    override def toGeno(pheno: String): Geno[Int] = {
      val gseq = pheno.toSeq.map(c => charToInt(c))
      Geno(gseq)
    }
  }

  case class Score(
                    minRating: Double,
                    meanRating: Double,
                    maxRating: Double,
                  )

  case class GAResultImpl(
                           score: Option[Score],
                           newPopulation: Seq[Geno[Int]]
                         ) extends GAResult[Score, Int]

  private def popToStr(pop: Seq[Geno[Int]]) = pop.map(p => p.genos.map(x => intToChar(x)).mkString("")).mkString("  ")

  //noinspection ScalaUnusedSymbol
  private def popsToStdout(popStream: Stream[GAResult[Score, Int]]): Unit =
    for ((pop, n) <- popStream.take(500).zipWithIndex) {
      val popStr = popToStr(pop.newPopulation)
      println(f"$n%4d ${pop.score.get.minRating}%7.1f ${pop.score.get.meanRating}%7.1f ${pop.score.get.maxRating}%7.1f   $popStr")
    }

  def ranAllele(ran: Random): Int = _alleles(ran.nextInt(_alleles.size))

  test("GA mutationOnly") {

    val r1 = new Random(987987L)
    val gat = new GA(new PhenoTesterT(), SelectionStrategies.mutationOnly(0.005, ranAllele, r1), new TransformerT)

    def randomGenome: Geno[Int] = {
      val gseq = (1 to 10).map(_ => ranAllele(r1))
      Geno(gseq)
    }

    val start: GAResult[Score, Int] = GAResultImpl(score = None, newPopulation = for (_ <- 1 to 10) yield randomGenome)

    val popStream = Stream.iterate(start)(r => gat.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }

  test("GA crossover") {

    val ran = new Random(987987L)
    val gaTest = new GA(new PhenoTesterT(), SelectionStrategies.crossover(0.005, ranAllele, ran), new TransformerT)

    def randomGenome: Geno[Int] = {
      val gseq = (1 to 10).map(_ => ranAllele(ran))
      Geno(gseq)
    }

    val start: GAResult[Score, Int] = GAResultImpl(newPopulation = for (_ <- 1 to 10) yield randomGenome, score = None)

    val popStream = Stream.iterate(start)(r => gaTest.nextPopulation(r.newPopulation))

    val r500 = popStream(500)
    r500.score.get.meanRating must be > 9.5

  }


  test("GeneticOps crossover 10") {
    val _ran = new Random(987987L)

    val go = new GeneticOps[String] {
      override def ran: Random = _ran
    }

    val n = 10
    val a = Geno(Seq.fill(n)("A"))
    val b = Geno(Seq.fill(n)("B"))
    val r = go.crossover(a, b)

    val (ra, rb) = r.genos.splitAt(8)
    ra mustBe Seq.fill(8)("A")
    rb mustBe Seq.fill(2)("B")
  }

  test("GeneticOps crossover 9") {
    val _ran = new Random(232L)

    val go = new GeneticOps[String] {
      override def ran: Random = _ran
    }

    val n = 9
    val a = Geno(Seq.fill(n)("A"))
    val b = Geno(Seq.fill(n)("B"))
    val r = go.crossover(a, b)

    val (ra, rb) = r.genos.splitAt(1)
    ra mustBe Seq.fill(1)("A")
    rb mustBe Seq.fill(8)("B")
  }

  test("selection strategy crossover") {

    val r = Random.javaRandomToRandom(new java.util.Random(293847L))

    def ra = (_: Random) => 0

    val strat = SelectionStrategies.crossover(0.0, ra, r)

    val tested = for (i <- 0 to 19) yield {
      (i.toDouble, Geno(Seq.fill(15)(i)))
    }

    val sel: Seq[Geno[Int]] = strat.select(tested)
    val dist = sel.map(s => s.genos.toSet)

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
      (i.toDouble, Geno(Seq.fill(15)(i)))
    }

    val sel: Seq[Geno[Int]] = strat.select(tested)
    val dist = sel.map(s => s.genos.distinct.sorted)

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

