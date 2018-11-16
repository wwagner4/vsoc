package vsoc.ga.trainga.ga.impl

import org.scalatest.{FunSuite, MustMatchers}

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
