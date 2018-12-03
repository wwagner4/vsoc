package vsoc.ga.trainga.ga.impl.team

import org.slf4j.LoggerFactory
import vsoc.ga.common.describe.{DescribableFormatter, PropertiesProvider}
import vsoc.ga.genetic._
import vsoc.ga.genetic.impl.SelectionStrategies
import vsoc.ga.matches.Team
import vsoc.ga.trainga.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.ga.{Data02, FitnessFunction1, TrainGa}
import vsoc.ga.trainga.nn.NeuralNet

import scala.util.Random

abstract class TrainGaAbstract extends TrainGa[Data02] with PropertiesProvider {

  private val log = LoggerFactory.getLogger(classOf[TrainGaAbstract])

  protected def createNeuralNet: () => NeuralNet

  protected def fitness: FitnessFunction1[Data02]

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

  protected lazy val tester: PhenoTester[TeamGa, Data02] = new PhenoTesterTeam(ran, fitness, testFactor)
  protected lazy val selStrat: SelectionStrategy[Double] = SelectionStrategies.crossover(mutationRate, randomAllele, ran)
  protected lazy val transformer: Transformer[Double, TeamGa] = new TransformerTeam(playerCount, createNeuralNet, inMapper, outMapper)

  lazy val ga: GaTeam[Double, TeamGa, Data02] = new GaTeam(tester, selStrat, fitness,  transformer)

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
      val initialPop: Seq[Seq[Double]] = population.getOrElse(createRandomPopGeno)
      var gar: GaReturnTeam[Data02, Double] = GAR(None, initialPop)
      var i = iterations.getOrElse(0)
      while (true) {
        i += 1
        gar = ga.nextPopulation(gar.newPopulation)
        val s = gar.score.map(d => f"${d.score}%.2f").getOrElse("-")
        log.info(f"finished iteration $i. populationScore: $s")
        iterations = Some(i)
        population = Some(gar.newPopulation)
        val score = gar.score.map(s => s.copy(
          trainGaId = trainGaId,
          trainGaNr = trainGaNr,
          iterations = i
        ))
        listeners.foreach(l => l.onIterationFinished(i, score))
      }
    } catch {
      case e: Exception =>
        val msg = s"Error running $trainGaId $trainGaNr ${e.getMessage}"
        log.error(msg, e)
    }
  }

  def teamsFromGeno(geno: Seq[Seq[Double]]): Seq[Team] = {
    geno.map(transformer.toPheno(_).vsocTeam)
  }


}


