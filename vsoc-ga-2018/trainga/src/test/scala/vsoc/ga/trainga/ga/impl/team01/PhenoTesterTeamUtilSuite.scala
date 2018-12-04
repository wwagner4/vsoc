package vsoc.ga.trainga.ga.impl.team01

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.matches.{MatchResult, MatchResults}
import vsoc.ga.trainga.ga.Data02
import vsoc.server.IVsocPlayer

class PhenoTesterTeamUtilSuite extends FunSuite with MustMatchers {


  test("result to data. Oner Player Two kicks") {

    val r: MatchResult = MatchResults.of(0,
      east = Seq(PT(kicks = 2)),
      west = Seq())

    val data: Data02 = PhenoTesterTeamUtil.resultToData(r.teamEastResult, r.teamWestResult)

    data.kicksMean mustBe 2
    data.kicksMax mustBe 2
    data.kicksMin mustBe 2

    data.kickOutMean mustBe 0
    data.kickOutMax mustBe 0
    data.kickOutMin mustBe 0

    data.ownGoalsMean mustBe 0
    data.ownGoalsMax mustBe 0
    data.ownGoalsMin mustBe 0

    data.otherGoalsMean mustBe 0
    data.otherGoalsMax mustBe 0
    data.otherGoalsMin mustBe 0

  }

  test("result to data. Two Players Two/Four kicks") {

    val r: MatchResult = MatchResults.of(0,
      east = Seq(PT(kicks = 2), PT(kicks = 4)),
      west = Seq())

    val data: Data02 = PhenoTesterTeamUtil.resultToData(r.teamEastResult, r.teamWestResult)

    data.kicksMean mustBe 3
    data.kicksMax mustBe 4
    data.kicksMin mustBe 2

    data.kickOutMean mustBe 0
    data.kickOutMax mustBe 0
    data.kickOutMin mustBe 0

    data.ownGoalsMean mustBe 0
    data.ownGoalsMax mustBe 0
    data.ownGoalsMin mustBe 0

    data.otherGoalsMean mustBe 0
    data.otherGoalsMax mustBe 0
    data.otherGoalsMin mustBe 0

    data.score mustBe 0.0
  }

  test("result to data. Own goals") {

    val r: MatchResult = MatchResults.of(0,
      east = Seq(PT(ownGoals = 3), PT(ownGoals = 1)),
      west = Seq(PT(otherGoals = 100), PT(otherGoals = 200)))

    val data: Data02 = PhenoTesterTeamUtil.resultToData(r.teamEastResult, r.teamWestResult)

    data.kicksMean mustBe 0
    data.kicksMax mustBe 0
    data.kicksMin mustBe 0

    data.kickOutMean mustBe 0
    data.kickOutMax mustBe 0
    data.kickOutMin mustBe 0

    data.ownGoalsMean mustBe 2
    data.ownGoalsMax mustBe 3
    data.ownGoalsMin mustBe 1

    data.otherGoalsMean mustBe 0
    data.otherGoalsMax mustBe 0
    data.otherGoalsMin mustBe 0

    data.goalDifference mustBe -304.0

    data.score mustBe 0.0
  }

  test("result to data. Other goals") {

    val r: MatchResult = MatchResults.of(0,
      east = Seq(PT(otherGoals = 1), PT(otherGoals = 2)),
      west = Seq(PT(otherGoals = 2), PT(otherGoals = 2)))

    val data: Data02 = PhenoTesterTeamUtil.resultToData(r.teamEastResult, r.teamWestResult)

    data.kicksMean mustBe 0
    data.kicksMax mustBe 0
    data.kicksMin mustBe 0

    data.kickOutMean mustBe 0
    data.kickOutMax mustBe 0
    data.kickOutMin mustBe 0

    data.ownGoalsMean mustBe 0
    data.ownGoalsMax mustBe 0
    data.ownGoalsMin mustBe 0

    data.otherGoalsMax mustBe 2
    data.otherGoalsMin mustBe 1
    data.otherGoalsMean mustBe 1.5

    data.goalDifference mustBe -1

    data.score mustBe 0.0
  }

  case class PT(
                 kicks: Int = 0,
                 kickOut: Int = 0,
                 otherGoals: Int = 0,
                 ownGoals: Int = 0,
               ) extends IVsocPlayer {
    override def getOwnGoalCount: Int = ownGoals

    override def getKickCount: Int = kicks

    override def getKickOutCount: Int = kickOut

    override def getOtherGoalCount: Int = otherGoals

    override def getNumber: Int = 0
  }
}

