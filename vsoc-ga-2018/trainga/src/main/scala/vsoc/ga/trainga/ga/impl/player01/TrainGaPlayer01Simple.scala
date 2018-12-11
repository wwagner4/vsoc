package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory

class TrainGaPlayer01Simple extends TrainGaPlayer01 {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01Simple])

  override def id: String = "trainGaPlayer01Simple"

  override def fullDesc: String =
    s"""${super.fullDesc}
       |Just players with no roles""".stripMargin

  override def createInitialPopGeno: Seq[Seq[Double]] = Seq(Seq.empty[Double])

  override def nextPopulation(iterNr: Int, popGeno: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]]) = {
    log.info(s"nextPop $iterNr")
    val score = new DataPlayer01(iterations = iterNr)
    val geno = Seq.empty[Seq[Double]]
    (score, geno)
  }



}
