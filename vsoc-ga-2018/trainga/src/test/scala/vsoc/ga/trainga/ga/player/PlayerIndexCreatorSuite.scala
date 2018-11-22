package vsoc.ga.trainga.ga.player

import org.scalatest.{FunSuite, MustMatchers}

class PlayerIndexCreatorSuite extends FunSuite with MustMatchers {

  test("length 12") {
    val c = new PlayerIndexCreator(12)
    val cs = ntimes(1000, c)
    cs.map(_._1).min mustBe 0
    cs.map(_._1).max mustBe 3
    cs.map(_._2).min mustBe 4
    cs.map(_._2).max mustBe 7
    cs.map(_._3).min mustBe 8
    cs.map(_._3).max mustBe 11
  }

  test("length 13") {
    val c = new PlayerIndexCreator(13)
    val cs = ntimes(1000, c)
    cs.map(_._1).min mustBe 0
    cs.map(_._1).max mustBe 4
    cs.map(_._2).min mustBe 5
    cs.map(_._2).max mustBe 8
    cs.map(_._3).min mustBe 9
    cs.map(_._3).max mustBe 12
  }

  test("length 14") {
    val c = new PlayerIndexCreator(14)
    val cs = ntimes(1000, c)
    cs.map(_._1).min mustBe 0
    cs.map(_._1).max mustBe 4
    cs.map(_._2).min mustBe 5
    cs.map(_._2).max mustBe 9
    cs.map(_._3).min mustBe 10
    cs.map(_._3).max mustBe 13
  }

  test("length 15") {
    val c = new PlayerIndexCreator(15)
    val cs = ntimes(1000, c)
    cs.map(_._1).min mustBe 0
    cs.map(_._1).max mustBe 4
    cs.map(_._2).min mustBe 5
    cs.map(_._2).max mustBe 9
    cs.map(_._3).min mustBe 10
    cs.map(_._3).max mustBe 14
  }


  def ntimes(n: Int, c: PlayerIndexCreator): Seq[(Int, Int, Int)] = {
    Seq.fill(n)(c.ran)
  }

}
