package vsoc.ga.matches

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.server.{PlayerWrapper, VsocPlayer, VsocPlayerEast}

class ResultSuite extends FunSuite with MustMatchers {

  test("create one player result initial") {

    val p = player(0)
    val east = Seq(p.player)

    val re = MatchResults.of(0, east, Seq.empty[VsocPlayer])
    re.matchSteps mustBe 0

    re.teamEastResult.kickCount mustBe 0
    re.teamEastResult.kickOutCount mustBe 0
    re.teamEastResult.otherGoalCount mustBe 0
    re.teamEastResult.ownGoalCount mustBe 0
    re.teamEastResult.playerResults.size mustBe 1

    val pre = re.teamEastResult.playerResults(0)
    pre.kickCount mustBe 0
    pre.kickOutCount mustBe 0
    pre.otherGoalCount mustBe 0
    pre.ownGoalCount mustBe 0
    pre.number mustBe 0

    re.teamWestResult.kickCount mustBe 0
    re.teamWestResult.kickOutCount mustBe 0
    re.teamWestResult.otherGoalCount mustBe 0
    re.teamWestResult.ownGoalCount mustBe 0
    re.teamWestResult.playerResults.size mustBe 0

  }

  def player(num: Int): PlayerWrapper = {
    val vsoc = new VsocPlayerEast(0, 0, 0)
    vsoc.setNumber(num)
    PlayerWrapper(vsoc)
  }

  test("create one player result increase own goal count") {

    val w = player(0)
    w.incEastGoalCount()
    val east = Seq(w.player)

    val re = MatchResults.of(0, east, Seq.empty[VsocPlayer])
    re.matchSteps mustBe 0

    re.teamEastResult.kickCount mustBe 0
    re.teamEastResult.kickOutCount mustBe 0
    re.teamEastResult.otherGoalCount mustBe 0
    re.teamEastResult.ownGoalCount mustBe 1
    re.teamEastResult.playerResults.size mustBe 1

    val pre = re.teamEastResult.playerResults(0)
    pre.kickCount mustBe 0
    pre.kickOutCount mustBe 0
    pre.otherGoalCount mustBe 0
    pre.ownGoalCount mustBe 1
    pre.number mustBe 0

    re.teamWestResult.kickCount mustBe 0
    re.teamWestResult.kickOutCount mustBe 0
    re.teamWestResult.otherGoalCount mustBe 0
    re.teamWestResult.ownGoalCount mustBe 0
    re.teamWestResult.playerResults.size mustBe 0

  }

  test("create one player result increase multiple values multiple times") {

    val eastPlayer1 = player(0)
    eastPlayer1.incWestGoalCount()
    eastPlayer1.incKickCount()

    val eastPlayer2 = player(1)
    eastPlayer2.incKickCount()
    eastPlayer2.incKickCount()

    val east = Seq(
      eastPlayer1.player,
      eastPlayer2.player
    )

    val re = MatchResults.of(0, east, Seq.empty[VsocPlayer])
    re.matchSteps mustBe 0

    re.teamEastResult.kickCount mustBe 3
    re.teamEastResult.kickOutCount mustBe 0
    re.teamEastResult.otherGoalCount mustBe 1
    re.teamEastResult.ownGoalCount mustBe 0
    re.teamEastResult.playerResults.size mustBe 2

    {
      val pre = re.teamEastResult.playerResults(0)
      pre.kickCount mustBe 1
      pre.kickOutCount mustBe 0
      pre.otherGoalCount mustBe 1
      pre.ownGoalCount mustBe 0
      pre.number mustBe 0
    }
    {
      val pre = re.teamEastResult.playerResults(1)
      pre.kickCount mustBe 2
      pre.kickOutCount mustBe 0
      pre.otherGoalCount mustBe 0
      pre.ownGoalCount mustBe 0
      pre.number mustBe 1
    }

    re.teamWestResult.kickCount mustBe 0
    re.teamWestResult.kickOutCount mustBe 0
    re.teamWestResult.otherGoalCount mustBe 0
    re.teamWestResult.ownGoalCount mustBe 0
    re.teamWestResult.playerResults.size mustBe 0

  }

  test("create one player result muliple teams") {

    val eastPlayer1 = player(0)
    eastPlayer1.incWestGoalCount()
    eastPlayer1.incKickCount()

    val eastPlayer2 = player(1)
    eastPlayer2.incKickCount()
    eastPlayer2.incKickCount()

    val east = Seq(
      eastPlayer1.player,
      eastPlayer2.player
    )

    val westPlayer1 = player(0)
    westPlayer1.incWestGoalCount()
    westPlayer1.incKickCount()

    val westPlayer2 = player(1)
    westPlayer2.incKickOutCount()
    westPlayer2.incKickOutCount()

    val west = Seq(
      westPlayer1.player,
      westPlayer2.player
    )

    val re = MatchResults.of(100, east, west)
    re.matchSteps mustBe 100

    re.teamEastResult.kickCount mustBe 3
    re.teamEastResult.kickOutCount mustBe 0
    re.teamEastResult.otherGoalCount mustBe 1
    re.teamEastResult.ownGoalCount mustBe 0
    re.teamEastResult.playerResults.size mustBe 2

    {
      val pre = re.teamEastResult.playerResults(0)
      pre.kickCount mustBe 1
      pre.kickOutCount mustBe 0
      pre.otherGoalCount mustBe 1
      pre.ownGoalCount mustBe 0
      pre.number mustBe 0
    }
    {
      val pre = re.teamEastResult.playerResults(1)
      pre.kickCount mustBe 2
      pre.kickOutCount mustBe 0
      pre.otherGoalCount mustBe 0
      pre.ownGoalCount mustBe 0
      pre.number mustBe 1
    }

    re.teamWestResult.kickCount mustBe 1
    re.teamWestResult.kickOutCount mustBe 2
    re.teamWestResult.otherGoalCount mustBe 1
    re.teamWestResult.ownGoalCount mustBe 0
    re.teamWestResult.playerResults.size mustBe 2

    {
      val pre = re.teamWestResult.playerResults(0)
      pre.kickCount mustBe 1
      pre.kickOutCount mustBe 0
      pre.otherGoalCount mustBe 1
      pre.ownGoalCount mustBe 0
      pre.number mustBe 0
    }
    {
      val pre = re.teamWestResult.playerResults(1)
      pre.kickCount mustBe 0
      pre.kickOutCount mustBe 2
      pre.otherGoalCount mustBe 0
      pre.ownGoalCount mustBe 0
      pre.number mustBe 1
    }

  }

}
