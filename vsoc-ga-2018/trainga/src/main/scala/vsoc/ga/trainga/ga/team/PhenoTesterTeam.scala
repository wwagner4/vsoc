package vsoc.ga.trainga.ga.team

import org.slf4j.LoggerFactory
import vsoc.ga.common.data.Data02
import vsoc.ga.common.describe.PropertiesProvider
import vsoc.ga.genetic.{PhenoTester, PhenoTesterResult}
import vsoc.ga.matches._
import vsoc.ga.trainga.ga.FitnessFunction
import vsoc.ga.trainga.ga.common.{TrainGaUtil, ValuesByIndexCollectorData02}

import scala.util.Random

class PhenoTesterTeam(
                       val ran: Random,
                       fitness: FitnessFunction[Data02],
                       testFactor: Int
                     )
  extends PhenoTester[TeamGa, Data02]
    with PropertiesProvider {

  private val log = LoggerFactory.getLogger(classOf[PhenoTesterTeam])

  def matchSteps: Int = 20000

  override def test(phenos: Seq[TeamGa]): PhenoTesterResult[TeamGa, Data02] = {
    val teamCnt = phenos.size
    log.info(s"testing $teamCnt teams")
    val coll = new ValuesByIndexCollectorData02()
    val _pairs: Seq[(Int, Int)] = TrainGaUtil.pairs(phenos.size, testFactor)
    for ((i1, i2) <- _pairs) {
      val (d1, d2) = TrainGaUtil.playMatch(phenos(i1).vsocTeam, phenos(i2).vsocTeam, fitness, matchSteps)
      val s1 = "%f.2" format d1.score
      val s2 = "%f.2" format d2.score
      log.info(s"finished match $s1 - $s2")
      coll.putValue(i1, d1)
      coll.putValue(i2, d2)
    }

    val _testedPhenos: Seq[(Data02, TeamGa)] = testedPhenos(coll, phenos)
    val _testedPhenos1: Seq[(Double, TeamGa)] = _testedPhenos.map { case (data, team) => (data.score, team) }
    val popTested = coll.meanAll

    new PhenoTesterResult[TeamGa, Data02] {

      override def testedPhenos: Seq[(Double, TeamGa)] = _testedPhenos1

      override def populationScore: Option[Data02] = Some(popTested)
    }
  }

  def str(result: TeamResult): String =
    s"kicks:${result.kickCount} kickOut:${result.kickOutCount}"

  def testedPhenos(coll: ValuesByIndexCollectorData02, phenos: Seq[TeamGa]): Seq[(Data02, TeamGa)] = {
    for (i <- phenos.indices) yield {
      (coll.mean(i), phenos(i))
    }
  }

  override def properties: Seq[(String, Any)] = Seq(
    ("matchsteps", matchSteps),
    ("matchfact", testFactor),
  )

  override def fullDesc: String =
    s"""Phenotester playing matches
       |$propsFmt
       | matchfact - defines the amount of matches played. amount of matches = matchfact * population size
    """.stripMargin

}


