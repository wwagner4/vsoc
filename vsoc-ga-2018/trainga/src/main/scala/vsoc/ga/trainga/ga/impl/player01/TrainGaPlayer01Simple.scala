package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.impl.{SelectionStrategies, UtilGa}
import vsoc.ga.matches.{Team, Teams}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

class TrainGaPlayer01Simple extends TrainGaPlayer01 {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01Simple])

  override def id: String = "trainGaPlayer01Simple"

  override def fullDesc: String =
    s"""${super.fullDesc}
       |Just players with no roles""".stripMargin

  val popSize = 30

  val ran = new Random()
  val net: NeuralNet = NeuralNets.team02
  val transformer = new TransformerPlayer01(net)
  val phenoTester = new PhenoTesterPlayer01
  val fitnessFunction = new FitnessFunctionPlayer01
  private val selection = SelectionStrategies.crossover(0.001, randomAllele, ran)

  override def createInitialPopGeno: Seq[Seq[Double]] = Seq.fill(popSize)(randomGeno(ran))

  def randomAllele(ran: Random): Double = 2.0 * ran.nextDouble() - 1.0

  def randomGeno(ran: Random): Seq[Double] = Seq.fill(net.getParam.length)(randomAllele(ran))

  override def nextPopulation(id: String, nr: String, iterNr: Int, popGeno: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]]) = {
    val phenos = popGeno map transformer.toPheno
    val testedPhenos: Seq[(DataPlayer01, PhenoPlayer01)] = phenoTester.test(phenos)

    val testedPhenosScore: Seq[(DataPlayer01, PhenoPlayer01)] = testedPhenos.map { case (s, p) => (s.copy(score = fitnessFunction.fitness(s)), p) }

    val ratedGenos: Seq[(Double, Seq[Double])] =
      testedPhenosScore.map { case (s, p) => (fitnessFunction.fitness(s), p.geno) }

    val score = UtilGa.meanScore(testedPhenosScore map { case (s, _) => s }, DataPlayer01Ops)
      .copy(
        id = id,
        nr = nr,
        iterations = iterNr,
      )
    val geno = selection.select(ratedGenos)
    log.info(s"next population ready $iterNr")

    (score, geno)
  }

  override def teamsFromPopulation: Seq[Team] = {
    val behavs: Seq[Behaviour] = population
      .map(geno => transformer.toPheno(geno))
      .map(p => p.behav)
    for (i <- 1 to 30) yield {
      val sbeh = ran.shuffle(behavs)
      Teams.behaviours(Seq(sbeh(0), sbeh(1), sbeh(2)), "" + i)
    }
  }


}
