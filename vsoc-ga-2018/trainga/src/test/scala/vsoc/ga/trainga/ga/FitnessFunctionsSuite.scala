package vsoc.ga.trainga.ga

import org.scalatest.FunSuite
import vsoc.ga.matches.{PlayerResult, TeamResult}

class FitnessFunctionsSuite extends FunSuite {

  test("kickingPlayers no kicks") {
    var tr: TeamResult = TR(Seq(PR(0), PR(0)))
    FitnessFunctions.kickingPlayers(tr) === 0
  }

  test("kickingPlayers no player result") {
    var tr: TeamResult = TR(Seq())
    FitnessFunctions.kickingPlayers(tr) === 0
  }

  test("kickingPlayers one player many kicks") {
    var tr: TeamResult = TR(Seq(PR(100), PR(0)))
    FitnessFunctions.kickingPlayers(tr) === 1
  }

  test("kickingPlayers both players kicks") {
    var tr: TeamResult = TR(Seq(PR(100), PR(1)))
    FitnessFunctions.kickingPlayers(tr) === 2
  }

  case class TR(_prs: Seq[PlayerResult]) extends TeamResult {

    override def ownGoalCount: Int = throw new NotImplementedError()

    override def otherGoalCount: Int = throw new NotImplementedError()

    override def kickOutCount: Int = throw new NotImplementedError()

    override def kickCount: Int = throw new NotImplementedError()

    override def playerResults: Seq[PlayerResult] = _prs
  }

  case class PR(_kk: Int) extends PlayerResult {

    override def number: Int = throw new NotImplementedError()

    override def ownGoalCount: Int = throw new NotImplementedError()

    override def otherGoalCount: Int = throw new NotImplementedError()

    override def kickOutCount: Int = throw new NotImplementedError()

    override def kickCount: Int = _kk
  }
}

