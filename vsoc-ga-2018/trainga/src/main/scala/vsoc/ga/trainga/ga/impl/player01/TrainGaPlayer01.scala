package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.ga.matches.Team
import vsoc.ga.trainga.ga.TrainGa

abstract class TrainGaPlayer01 extends TrainGa[DataPlayer01] {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01])

  override def fullDesc: String = s"Training of players"

  override def teamsFromPopulation: Seq[Team] = ???

  var cnt = 0;

  override def run(trainGaId: String, trainGaNr: String): Unit = {
    log.info(s"start $trainGaId $trainGaNr")
    try {
      if (population.isEmpty) {
        population = createInitialPopGeno
      }
      while (true) {
        Thread.sleep(1000)
        val (score, nextPop) = nextPopulation(iterations, population)
        iterations += 1
        listeners.foreach(l => l.onIterationFinished(iterations, Some(score)))
      }
    } catch {
      case e: Exception =>
        val msg = s"Error running $trainGaId $trainGaNr ${e.getMessage}"
        log.error(msg, e)
    }
  }

  def createInitialPopGeno: Seq[Seq[Double]]

  def nextPopulation(iterNr: Int, population: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]])

  def id: String


}
