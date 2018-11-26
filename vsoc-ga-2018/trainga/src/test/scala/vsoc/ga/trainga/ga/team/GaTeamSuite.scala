package vsoc.ga.trainga.ga.team

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.trainga.ga.common.TrainGaUtil

import scala.util.Random

class GaTeamSuite extends FunSuite with MustMatchers {

  val ran = new Random(239847294L)

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
        val _pairs: Seq[(Int, Int)] = TrainGaUtil.pairs(size, 2)
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
