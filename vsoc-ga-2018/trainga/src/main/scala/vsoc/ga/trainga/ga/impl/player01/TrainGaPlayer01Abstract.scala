package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.impl.{SelectionStrategies, UtilGa}
import vsoc.ga.matches.nn.NeuralNetFactories
import vsoc.ga.matches.{Team, Teams}
import vsoc.ga.trainga.ga.{FitnessFunctions, TrainGa, TrainGaFitnessFunction}

import scala.util.Random

abstract class TrainGaPlayer01Abstract extends TrainGa[DataPlayer01] {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01Abstract])

  def id: String

  def fullDesc: String

  private val popSize = 30
  private val ran = new Random()
  protected def neuralNetFactory = NeuralNetFactories.team02
  private val transformer = new TransformerPlayer01(neuralNetFactory)
  private val phenoTester = new PhenoTesterPlayer01
  protected def fitnessFunction: TrainGaFitnessFunction[DataPlayer01] = FitnessFunctions.dataPlayer01A
  private val selection = SelectionStrategies.crossover(0.001, neuralNetFactory.randomAllele, ran)

  override def run(trainGaId: String, trainGaNr: String): Unit = {
    log.info(s"start $trainGaId $trainGaNr")
    try {
      if (population.isEmpty) {
        population = createInitialPopGeno
      }
      while (true) {
        val (score, nextPop) = nextPopulation(trainGaId, trainGaNr, iterations, population)
        iterations += 1
        listeners.foreach(l => l.onIterationFinished(iterations, Some(score)))
        population = nextPop
      }
    } catch {
      case e: Exception =>
        val msg = s"Error running $trainGaId $trainGaNr ${e.getMessage}"
        log.error(msg, e)
    }
  }

  private def createInitialPopGeno: Seq[Seq[Double]] = Seq.fill(popSize)(randomGeno(ran))

  private def randomGeno(ran: Random): Seq[Double] = Seq.fill(neuralNetFactory.parameterSize)(neuralNetFactory.randomAllele(ran))

  private def nextPopulation(id: String, nr: String, iterNr: Int, popGeno: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]]) = {
    val phenos: Seq[PhenoPlayer01] = popGeno map transformer.toPheno
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
