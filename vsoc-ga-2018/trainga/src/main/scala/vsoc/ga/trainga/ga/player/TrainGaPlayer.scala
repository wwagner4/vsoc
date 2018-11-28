package vsoc.ga.trainga.ga.player

import org.slf4j.LoggerFactory
import vsoc.ga.common.data.Data02
import vsoc.ga.genetic.{Transformer, _}
import vsoc.ga.matches._
import vsoc.ga.trainga.ga.common.ValuesByIndexCollectorData02
import vsoc.ga.trainga.ga.team.TrainGaTeamUtil
import vsoc.ga.trainga.ga.{FitnessFunction, FitnessFunctions, TrainGa}

import scala.util.Random

class TrainGaPlayer extends TrainGa[Data02] {

  val ran = new Random()

  val stepsPerMatch = 20000

  val matchesPerTest = 20

  val populationSize = 30

  val genoSize = 555 // Depends on NN

  val fitness: FitnessFunction[Data02] = FitnessFunctions.data02A05

  val tester: PhenoTester[PlayerPheno, Data02] = createTester

  val selStrategy: SelectionStrategy[Double] = createSelStrategy

  val transformer: Transformer[Double, PlayerPheno] = createTransformer

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer])

  override def id: String = "TrainGaPlayer"

  override def fullDesc: String = "TrainGaPlayer"

  override def teamsFromGeno(geno: Seq[Geno[Double]]): Seq[Team] = ???

  override def run(trainGaId: String, trainGaNr: String): Unit = {

    val ga = new GA(tester, selStrategy, transformer)
    var gaResult = initialGaResult
    while (true) {
      gaResult = ga.nextPopulation(gaResult.newPopulation)
    }
  }

  def randomAllele(_ran: Random): Double = 2.0 * _ran.nextDouble() - 1.0


  def initialGaResult: GAResult[Data02, Double] = {

    def initialPopGeno: PopGeno[Double] = {

      def ranGeno: Geno[Double] = {
        val alleles: Seq[Double] = Seq.fill(genoSize)(randomAllele(ran))
        Geno(alleles)
      }

      val genos: Seq[Geno[Double]] = Seq.fill(populationSize)(ranGeno)

      PopGeno(genos)
    }

    new GAResult[Data02, Double] {
      override def score: Option[Data02] = None

      override def newPopulation: PopGeno[Double] = initialPopGeno
    }
  }

  def createTester: PhenoTester[PlayerPheno, Data02] = {

    new PhenoTester[PlayerPheno, Data02]() {

      def createTeam(p1: PlayerPheno, p2: PlayerPheno, p3: PlayerPheno): Team = {
        Teams.behaviours(Seq(p1.behaviour, p2.behaviour, p3.behaviour), "anonymous")
      }

      override def test(phenos: Seq[PlayerPheno]): PhenoTesterResult[PlayerPheno, Data02] = {

        val ic = new PlayerIndexCreator(phenos.size, ran)
        val coll = new ValuesByIndexCollectorData02()

        for (i <- 1 to matchesPerTest) {
          val (i11, i12, i13) = ic.ran
          val (i21, i22, i23) = ic.ran
          val t1 = createTeam(phenos(i11), phenos(i12), phenos(i13))
          val t2 = createTeam(phenos(i21), phenos(i22), phenos(i23))
          val (d1, d2) = TrainGaTeamUtil.playMatch(t1, t2, fitness, stepsPerMatch);
          val s1 = "%f.2" format d1.score
          val s2 = "%f.2" format d2.score

          log.info(s"finished match $s1 - $s2")

          coll.putValue(i11, d1)
          coll.putValue(i12, d1)
          coll.putValue(i13, d1)

          coll.putValue(i21, d2)
          coll.putValue(i22, d2)
          coll.putValue(i23, d2)
        }

        val retested = for((p, i) <- phenos.zipWithIndex) yield {
          val data = coll.mean(i)
          (data.score, p)
        }

        val rescore = coll.meanAll

        new PhenoTesterResult[PlayerPheno, Data02] {
          override def testedPhenos: Seq[(Double, PlayerPheno)] = retested

          override def populationScore: Option[Data02] = Some(rescore)
        }
      }

      override def fullDesc: String = "Player:PhenoTester"
    }


  }

  def createSelStrategy: SelectionStrategy[Double] = SelectionStrategies.crossover(0.001, randomAllele, ran)

  def createTransformer: Transformer[Double, PlayerPheno] = new Transformer[Double, PlayerPheno]() {

    def toPheno(geno: Geno[Double]): PlayerPheno = ???

    def toGeno(pheno: PlayerPheno): Geno[Double] = ???

  }


}


/**
  * Selects players for a team
  * In order to create some kinds of player roles player 1
  * is selected from the first third of population, player 2
  * is selcted from the second third of the population and
  * player 3 from the third third.
  * To understand this have a look at the testcases.
  */
class PlayerIndexCreator(popSize: Int, _ran: Random) {

  val ms = math.floor(popSize.toDouble / 3).toInt
  val diff = popSize - (ms * 3)
  val (amin, amax, bmin, bmax, cmin, cmax) =
    if (diff == 0)
      (0, ms - 1, ms, 2 * ms - 1, 2 * ms, 3 * ms - 1)
    else if (diff == 1)
      (0, ms, ms + 1, 2 * ms, 2 * ms + 1, 3 * ms)
    else
      (0, ms, ms + 1, 2 * ms + 1, 2 * ms + 2, 3 * ms + 1)

  def ran: (Int, Int, Int) = {
    (amin + _ran.nextInt(amax - amin + 1),
      bmin + _ran.nextInt(bmax - bmin + 1),
      cmin + _ran.nextInt(cmax - cmin + 1))
  }

}

