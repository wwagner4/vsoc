package vsoc.ga.trainga.ga.team

import org.slf4j.LoggerFactory
import vsoc.ga.common.data.Data02
import vsoc.ga.common.describe.PropertiesProvider
import vsoc.ga.genetic.{PhenoTester, PhenoTesterResult}
import vsoc.ga.matches._
import vsoc.ga.trainga.ga.FitnessFunction
import vsoc.ga.trainga.ga.common.TrainGaUtil

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
    val mrc = new PhenoTesterTeamCollector[Data02]()
    val _pairs: Seq[(Int, Int)] = TrainGaUtil.pairs(phenos.size, testFactor)
    for ((i1, i2) <- _pairs) {
      val (d1, d2) = TrainGaUtil.playMatch(phenos(i1).vsocTeam, phenos(i2).vsocTeam, fitness, matchSteps)
      val s1 = "%f.2" format d1.score
      val s2 = "%f.2" format d2.score
      log.info(s"finished match $s1 - $s2")
      mrc.addResult(i1, d1)
      mrc.addResult(i2, d2)
    }

    val _testedPhenos: Seq[(Data02, TeamGa)] = testedPhenos(mrc, phenos)
    val _testedPhenos1: Seq[(Double, TeamGa)] = _testedPhenos.map { case (data, team) => (data.score, team) }

    new PhenoTesterResult[TeamGa, Data02] {

      override def testedPhenos: Seq[(Double, TeamGa)] = _testedPhenos1

      override def populationScore: Option[Data02] = Some(createPopulationScore(_testedPhenos))
    }
  }

  def str(result: TeamResult): String =
    s"kicks:${result.kickCount} kickOut:${result.kickOutCount}"

  def testedPhenos(mcr: PhenoTesterTeamCollector[Data02], phenos: Seq[TeamGa]): Seq[(Data02, TeamGa)] = {
    val results: Map[Int, Seq[Data02]] = mcr.results
    for (i <- phenos.indices) yield {
      val rs: Seq[Data02] = results(i)
      require(rs.nonEmpty)
      val mean = TrainGaUtil.mean(rs)
      (mean, phenos(i))
    }
  }

  def createPopulationScore(testedPhenos: Seq[(Data02, TeamGa)]): Data02 = {
    TrainGaUtil.mean(testedPhenos.map(p => p._1))
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

class PhenoTesterTeamCollector[T] {

  private var map = Map.empty[Int, Seq[T]]

  def addResult(i: Int, result: T): Unit = {
    if (map.contains(i)) {
      map = map + (i -> (map(i) :+ result))
    } else {
      map = map + (i -> Seq(result))
    }
  }

  def results: Map[Int, Seq[T]] = map

}

