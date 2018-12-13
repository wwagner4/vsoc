package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory

class TrainGaPlayer01Simple extends TrainGaPlayer01 {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01Simple])

  override def id: String = "trainGaPlayer01Simple"

  override def fullDesc: String =
    s"""${super.fullDesc}
       |Just players with no roles""".stripMargin

  val transformer = new TransformerPlayer01
  val phenoTester = new PhenoTesterPlayer01

  override def createInitialPopGeno: Seq[Seq[Double]] = ???

  override def nextPopulation(iterNr: Int, popGeno: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]]) = {
    val phenos = popGeno map transformer.toPheno
    val testedPhenos = phenoTester.test(phenos)

    // fitness function
    // genetic operations

    log.info(s"nextPop $iterNr")
    val score = ???
    val geno = ???
    (score, geno)
  }

}
