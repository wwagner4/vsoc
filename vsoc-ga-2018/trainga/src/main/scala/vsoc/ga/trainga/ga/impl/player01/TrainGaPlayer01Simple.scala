package vsoc.ga.trainga.ga.impl.player01

import org.slf4j.LoggerFactory
import vsoc.ga.genetic.impl.{SelectionStrategies, UtilGa}

import scala.util.Random

class TrainGaPlayer01Simple extends TrainGaPlayer01 {

  private val log = LoggerFactory.getLogger(classOf[TrainGaPlayer01Simple])

  override def id: String = "trainGaPlayer01Simple"

  override def fullDesc: String =
    s"""${super.fullDesc}
       |Just players with no roles""".stripMargin
  val popSize = 30

  val ran = new Random()
  val transformer = new TransformerPlayer01
  val phenoTester = new PhenoTesterPlayer01
  val fitnessFunction = new FitnessFunctionPlayer01
  private val selection = SelectionStrategies.crossover(0.001, randomAllele, ran)

  override def createInitialPopGeno: Seq[Seq[Double]] = Seq.fill(popSize)(randomGeno(ran))

  def randomAllele(ran: Random): Double = 2.0 * ran.nextDouble() - 1.0
  def randomGeno(ran: Random): Seq[Double] = Seq.fill(200)(randomAllele(ran))

  override def nextPopulation(id: String, nr: String, iterNr: Int, popGeno: Seq[Seq[Double]]): (DataPlayer01, Seq[Seq[Double]]) = {
    val phenos = popGeno map transformer.toPheno
    val testedPhenos: Seq[(DataPlayer01, PhenoPlayer01)] = phenoTester.test(phenos)
    val ratedGenos: Seq[(Double, Seq[Double])] =
      testedPhenos.map{case (s, p) => (fitnessFunction.fitness(s), p.geno)}



    val score = UtilGa.meanScore( testedPhenos map {case (s, _) => s}, DataPlayer01Ops)
      .copy(
        id = id,
        nr = nr,
        iterations = iterNr,
      )
    val geno = selection.select(ratedGenos)
    log.info(s"next population ready $iterNr")

    (score, geno)
  }

}
