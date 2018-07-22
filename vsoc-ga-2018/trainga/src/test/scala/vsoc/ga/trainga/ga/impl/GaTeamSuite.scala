package vsoc.ga.trainga.ga.impl

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.common.data.Data02

import scala.util.Random

class GaTeamSuite extends FunSuite with MustMatchers {

  val ran = new Random(239847294L)

  test("PhenoTesterTeamCollector one id") {
    val c = new PhenoTesterTeamCollector[String]()

    c.addResult(0, "A")
    c.addResult(0, "B")
    c.addResult(0, "C")

    val r: Map[Int, Seq[String]] = c.results

    r.size mustBe 1
    r(0).size mustBe 3
    r(0).contains("A") mustBe true
    r(0).contains("B") mustBe true
    r(0).contains("C") mustBe true

  }

  test("PhenoTesterTeamCollector two ids") {
    val c = new PhenoTesterTeamCollector[String]()

    c.addResult(0, "A")
    c.addResult(1, "B")
    c.addResult(0, "C")
    c.addResult(0, "Hallo")

    val r: Map[Int, Seq[String]] = c.results

    r.size mustBe 2
    r(0).size mustBe 3
    r(0).contains("A") mustBe true
    r(0).contains("C") mustBe true
    r(0).contains("Hallo") mustBe true

    r(1).size mustBe 1
    r(1).contains("B") mustBe true

  }

  test("PhenoTesterTeamUtil sum seq one") {
    val d1 = Data02(
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val d2 = PhenoTesterTeamUtil.sum(Seq(d1))

    d2 mustBe d1

  }

  test("PhenoTesterTeamUtil sum seq multi") {
    val d1 = Data02(
      trainGaId = "a",
      trainGaNr = "b",
      iterations = 1110,
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val d2 = Data02(
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val d3 = Data02(
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val dr = Data02(
      trainGaId = "a",
      trainGaNr = "b",
      iterations = 1110,
      kicksMax = 3.0,
      kicksMean = 6.0,
      kicksMin = 9.0,
      kickOutMax = 300.0,
      kickOutMean = 600.0,
      kickOutMin = 900.0,
      otherGoalsMax = 12.0,
      otherGoalsMean = 15.0,
      otherGoalsMin = 18.0,
      ownGoalsMax = 21.0,
      ownGoalsMean = 24.0,
      ownGoalsMin = 27.0,
      goalDifference = 30.0,
      score = 33.0
    )

    val d = PhenoTesterTeamUtil.sum(Seq(d1, d2, d3))

    d mustBe dr

  }

  test("PhenoTesterTeamUtil sum") {
    val d1 = Data02(
      trainGaId = "d1id",
      trainGaNr = "d1nr",
      iterations = 555,
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val d2 = Data02(
      trainGaId = "d2id",
      trainGaNr = "d2nr",
      iterations = 111,
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val d3 = PhenoTesterTeamUtil.sum(d1, d2)

    d3.trainGaId mustBe d1.trainGaId
    d3.trainGaNr mustBe d1.trainGaNr
    d3.iterations mustBe d1.iterations

    d3.kicksMax mustBe 2.0
    d3.kicksMean mustBe 4.0
    d3.kicksMin mustBe 6.0
    d3.kickOutMax mustBe 200.0
    d3.kickOutMean mustBe 400.0
    d3.kickOutMin mustBe 600.0
    d3.otherGoalsMax mustBe 8.0
    d3.otherGoalsMean mustBe 10.0
    d3.otherGoalsMin mustBe 12.0
    d3.ownGoalsMax mustBe 14.0
    d3.ownGoalsMean mustBe 16.0
    d3.ownGoalsMin mustBe 18.0
    d3.goalDifference mustBe 20.0
    d3.score mustBe 22.0
  }


  test("PhenoTesterTeamUtil div") {
    val d1 = Data02(
      trainGaId = "a",
      trainGaNr = "b",
      iterations = 1110,
      kicksMax = 1.0,
      kicksMean = 2.0,
      kicksMin = 3.0,
      kickOutMax = 100.0,
      kickOutMean = 200.0,
      kickOutMin = 300.0,
      otherGoalsMax = 4.0,
      otherGoalsMean = 5.0,
      otherGoalsMin = 6.0,
      ownGoalsMax = 7.0,
      ownGoalsMean = 8.0,
      ownGoalsMin = 9.0,
      goalDifference = 10.0,
      score = 11.0
    )

    val dr = Data02(
      trainGaId = "a",
      trainGaNr = "b",
      iterations = 1110,
      kicksMax = 0.5,
      kicksMean = 1.0,
      kicksMin = 1.5,
      kickOutMax = 50.0,
      kickOutMean = 100.0,
      kickOutMin = 150.0,
      otherGoalsMax = 2.0,
      otherGoalsMean = 2.5,
      otherGoalsMin = 3,
      ownGoalsMax = 3.5,
      ownGoalsMean = 4,
      ownGoalsMin = 4.5,
      goalDifference = 5,
      score = 5.5
    )

    val d = PhenoTesterTeamUtil.div(d1, 2.0)

    d mustBe dr

  }


  val sizes = Seq(2, 3, 4, 5, 6, 7, 8, 9, 10, 20)

  def containsAll(seq: Seq[Int], values: Seq[Int]): Boolean = {
    values.forall(a => seq.contains(a))
  }

  def contaisEqual(pairs: Seq[(Int, Int)]):Boolean = {
    !pairs.forall(t => t._1 != t._2)
  }

  for (size <- sizes) {
    test("pairs " + size) {
      val requiredIndexes: Seq[Int] = 0 until size
      for (n <- 1 to 1000) {
        val _pairs: Seq[(Int, Int)] = PhenoTesterTeamUtil.pairs(size, 2)
        val ls = _pairs.map(_._1)
        val rs = _pairs.map(_._2)
        val tested = (ls ++ rs).distinct.sorted
        if (contaisEqual(_pairs)) {
          val pairsStr = _pairs.map(t => s"[${t._1}, ${t._2}]")
          fail(s"In $n an equal pair was found \n${pairsStr.mkString("\n")}")
        }
        if (!containsAll(tested, requiredIndexes)) {
          val pairsStr = _pairs.map(t => s"[${t._1}, ${t._2}]")
          val missing = requiredIndexes.toSet -- tested
          fail(s"In $n the following indexes $missing are missing in \n${pairsStr.mkString("\n")}")
        }
      }
    }

  }


}
