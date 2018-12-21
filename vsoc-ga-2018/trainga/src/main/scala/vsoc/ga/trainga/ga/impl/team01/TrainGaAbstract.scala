package vsoc.ga.trainga.ga.impl.team01

import org.slf4j.LoggerFactory
import vsoc.ga.common.describe.{DescribableFormatter, PropertiesProvider}
import vsoc.ga.genetic._
import vsoc.ga.genetic.impl.{SelectionStrategies, UtilGa}
import vsoc.ga.matches.Team
import vsoc.ga.matches.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.matches.nn.NeuralNet
import vsoc.ga.trainga.ga._

import scala.util.Random

abstract class TrainGaAbstract extends TrainGa[Data02] with PropertiesProvider {

  private val log = LoggerFactory.getLogger(classOf[TrainGaAbstract])

  protected def createNeuralNet: () => NeuralNet

  protected def fitness: TrainGaFitnessFunction[Data02]

  protected def testFactor: Int = 2

  protected def populationSize: Int = 10

  protected def mutationRate: Double = 0.001

  protected val playerCount = 3

  protected def inMapper: InputMapperNn

  protected def outMapper: OutputMapperNn

  private lazy val nnTempl = createNeuralNet()

  private val playerParamSize: Int = nnTempl.getParam.length

  def ran: Random = Random.javaRandomToRandom(new java.util.Random())

  override def properties: Seq[(String, Any)] = Seq(
    ("player cnt", playerCount),
    ("pop size", populationSize),
    ("test factor", testFactor),
    ("matches", populationSize * testFactor),
    ("mut rate", mutationRate),
    ("nn", nnTempl),
    ("fit func", fitness),
  )

  def fullDescHeading: String

  override def fullDesc: String = {
    s"""'$id' - $fullDescHeading
       |${DescribableFormatter.format(properties, 0)}
    """.stripMargin
  }

  private case class GAR(
                          score: Option[Data02],
                          newPopulation: Seq[Seq[Double]]
                        ) extends GaReturnTeam[Data02, Double]

  def randomAllele(_ran: Random): Double = 2.0 * _ran.nextDouble() - 1.0

  protected lazy val tester: PhenoTester[PhenoTeam, Double, Data02] = new PhenoTesterTeam(ran, fitness, testFactor)
  protected lazy val selStrat: SelectionStrategy[Double] = SelectionStrategies.crossover(mutationRate, randomAllele, ran)
  protected lazy val transformer: Transformer[Double, PhenoTeam] = new TransformerTeam(playerCount, createNeuralNet, inMapper, outMapper)

  lazy val ga: GaTeam[Double, PhenoTeam, Data02] = new GaTeam(tester, selStrat, fitness, transformer, UtilGa.meanScore(_, Data02Ops))

  def createRandomPopGeno: Seq[Seq[Double]] = {
    def ranSeq(size: Int): Seq[Double] =
      (1 to size).map(_ => randomAllele(ran))

    def createRandomTeamGeno: Seq[Double] =
      (1 to playerCount).flatMap { _ =>
        ranSeq(playerParamSize)
      }

    (1 to populationSize).map { _ =>
      createRandomTeamGeno
    }
  }

  override def run(trainGaId: String, trainGaNr: String): Unit = {
    log.info(s"start GaTeam populationSize: $populationSize playerCount: $playerCount playerParamSize:$playerParamSize")
    try {
      val initialPop: Seq[Seq[Double]] = if (population.isEmpty) createRandomPopGeno else population
      var gar: GaReturnTeam[Data02, Double] = GAR(None, initialPop)
      while (true) {
        gar = ga.nextPopulation(gar.newPopulation)
        val s = gar.score.map(d => f"${d.score}%.2f").getOrElse("-")
        log.info(f"finished iteration $iterations. populationScore: $s")
        population = gar.newPopulation
        val score = gar.score.map(s => s.copy(
          trainGaId = trainGaId,
          trainGaNr = trainGaNr,
          iterations = iterations,
        ))
        listeners.foreach(l => l.onIterationFinished(iterations, score))
        iterations += 1
      }
    } catch {
      case e: Exception =>
        val msg = s"Error running $trainGaId $trainGaNr ${e.getMessage}"
        log.error(msg, e)
    }
  }

  def teamsFromPopulation: Seq[Team] = {
    population.map(transformer.toPheno(_).vsocTeam)
  }


}


