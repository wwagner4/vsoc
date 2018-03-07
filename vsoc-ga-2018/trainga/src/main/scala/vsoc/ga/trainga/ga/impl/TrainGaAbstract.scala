package vsoc.ga.trainga.ga.impl

import org.slf4j.LoggerFactory
import vsoc.ga.genetic.{GA, GAResult, SelectionStrategies}
import vsoc.ga.matches.{Team, TeamResult}
import vsoc.ga.trainga.ga.TrainGa
import vsoc.ga.trainga.nn.NeuralNet

import scala.util.Random

abstract class TrainGaAbstract extends TrainGa[Double] {

  private val log = LoggerFactory.getLogger(classOf[TrainGaAbstract])

  protected def createNeuralNet: () => NeuralNet

  protected def ran: Random

  protected def fitness: TeamResult => Double

  private val playerCount = 3

  private val populationSize = 20

  private val playerParamSize: Int = createNeuralNet().getParam.length

  private case class GAR(
                          score: Option[Double],
                          newPopulation: Seq[Seq[Double]]
                        ) extends GAResult[Double, Double]

  def randomAllele(_ran: Random): Double = 2.0 * _ran.nextDouble() - 1.0

  lazy val transformer = new TransformerTeam(playerCount, createNeuralNet)

  val ga: GA[Double, TeamGa, Double] = new GA(
    new PhenoTesterTeam(ran, fitness),
    SelectionStrategies.crossover(0.001, randomAllele, ran),
    transformer)

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

  def debug(pop: Seq[Seq[Double]]): Unit = {
    val len = pop(0).size
    val ids = Random.shuffle((0 until len).toList.take(20))
    val red = pop.map(s => ids.map(i => "%.2f" format s(i)))
    println("*** REDUCED: " + red)
  }

  private def pause(ms: Int): Unit = Thread.sleep(ms)

  override def run(trainGaId: String, trainGaNr: String): Unit = {
    log.info(s"start GA populationSize: $populationSize playerCount: $playerCount playerParamSize:$playerParamSize")
    try {
      val initialPop: Seq[Seq[Double]] = population.getOrElse(createRandomPopGeno)
      var gar: GAResult[Double, Double] = GAR(None, initialPop)
      var i = iterations.getOrElse(0)
      while (true) {
        i += 1
        //debug(gar.newPopulation)
        gar = ga.nextPopulation(gar.newPopulation)
        val s = gar.score.map(s => f"$s%.2f").getOrElse("-")
        log.info(f"finished iteration $i. score: $s")
        iterations = Some(i)
        population = Some(gar.newPopulation)
        val data: Seq[(String, Any)] = Seq(
          ("trainGaId", trainGaId),
          ("trainGaNr", trainGaNr),
          ("iterations", i),
          ("score", gar.score.getOrElse(0.0))
        )
        listeners.foreach(l => l.onIterationFinished(i, gar.score, data))
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


