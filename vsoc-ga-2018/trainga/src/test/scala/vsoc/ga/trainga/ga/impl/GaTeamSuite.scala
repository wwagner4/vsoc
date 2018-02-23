package vsoc.ga.trainga.ga.impl

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.matches.{PlayerResult, TeamResult}
import vsoc.ga.trainga.ga.FitnessFunctions

import scala.util.Random

class GaTeamSuite extends FunSuite with MustMatchers {

  val ran = new Random(239847294L)


  test("PhenoTesterTeam fitness 10 kicks") {
    val mr: TeamResult = new TeamResult {

      override def otherGoalCount: Int = 0

      override def kickCount: Int = 10

      override def playerResults: Seq[PlayerResult] = Seq.empty[PlayerResult]

      override def ownGoalCount: Int = 0

      override def kickOutCount: Int = 0
    }

    val fit = FitnessFunctions.fitnessConsiderAll01(mr)

    fit mustBe 100.0 +- 0.0001
  }

  test("PhenoTesterTeam fitness 5 goals") {
    val mr: TeamResult = new TeamResult {

      override def otherGoalCount: Int = 5

      override def kickCount: Int = 0

      override def playerResults: Seq[PlayerResult] = Seq.empty[PlayerResult]

      override def ownGoalCount: Int = 0

      override def kickOutCount: Int = 0
    }

    val fit = FitnessFunctions.fitnessConsiderAll01(mr)

    fit mustBe 500.0 +- 0.0001
  }

  test("PhenoTesterTeam fitness 3 own goals") {
    val mr: TeamResult = new TeamResult {

      override def otherGoalCount: Int = 0

      override def kickCount: Int = 0

      override def playerResults: Seq[PlayerResult] = Seq.empty[PlayerResult]

      override def ownGoalCount: Int = 3

      override def kickOutCount: Int = 0
    }

    val fit = FitnessFunctions.fitnessConsiderAll01(mr)

    fit mustBe -300.0 +- 0.0001
  }

}
