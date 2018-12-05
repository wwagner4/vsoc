package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.ga.matches.Team
import vsoc.ga.trainga.ga.TrainGa
import vsoc.ga.trainga.ga.impl.team01.TrainGaAbstract

class TrainGaPlayer01 extends TrainGa[DataPlayer01] {

  private val log = LoggerFactory.getLogger(classOf[TrainGaAbstract])

  override def id: String = "trainGaPlayer01"

  override def teamsFromGeno(geno: Seq[Seq[Double]]): Seq[Team] = ???

  var cnt = 0;

  override def run(trainGaId: String, trainGaNr: String): Unit = {
    log.info(s"start TrainGaPlayer01")
    val score = Option.empty[DataPlayer01]
    try {
      if (population.isEmpty) {
        population = Some(createRandomPopGeno)
      }
      if (iterations.isEmpty) {
        iterations = Some(0)
      }
      while (true) {
        listeners.foreach(l => l.onIterationFinished(iterations.get, score))
        Thread.sleep(1000)
        iterations = Some(iterations.get + 1)
      }
    } catch {
      case e: Exception =>
        val msg = s"Error running $trainGaId $trainGaNr ${e.getMessage}"
        log.error(msg, e)
    }
  }

  override def fullDesc: String = "TrainGaPlayer01"

  def createRandomPopGeno: Seq[Seq[Double]] = Seq(Seq.empty[Double])

}
