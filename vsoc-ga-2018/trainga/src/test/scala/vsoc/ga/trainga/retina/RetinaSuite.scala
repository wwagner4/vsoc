package vsoc.ga.trainga.retina

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.behaviour.{DistDirVision, Sensors}
import vsoc.ga.matches.retina.Retinas
import vsoc.ga.matches.retina.impl.Functions

class RetinaSuite extends FunSuite with MustMatchers {

  private val data = Seq(
    (-45, Array(0.5, 0.0, 0.0, 0.0, 0.0)),
    (-40, Array(0.78, 0.0, 0.0, 0.0, 0.0)),
    (-30, Array(0.65, 0.35, 0.0, 0.0, 0.0)),
    (-20, Array(0.11, 0.9, 0.0, 0.0, 0.0)),
    (-10, Array(0.0, 0.55, 0.44, 0.0, 0.0)),
    (0, Array(0.0, 0.0, 1.0, 0.0, 0.0)),
    (10, Array(0.0, 0.0, 0.44, 0.55, 0.0)),
    (20, Array(0.0, 0.0, 0.0, 0.9, 0.11)),
    (30, Array(0.0, 0.0, 0.0, 0.35, 0.65)),
    (40, Array(0.0, 0.0, 0.0, 0.0, 0.78)),
    (45, Array(0.0, 0.0, 0.0, 0.0, 0.5)),
  )

  val tolerance = 0.2
  val retinasFactory = Retinas()
  val retinasFactory05 = Retinas(0.5)

  test("one value") {
    val dir = -45
    val dist = 10
    val r = retinasFactory.ball(0, 5)
    val sens = ballSens(dir, dist)
    val a = Array(0.0, 0.0, 0.0, 0.0, 0.0)
    r.see(sens, a)

    val v = 9 * 0.5
    a(0) mustBe v +- tolerance
  }

  test("offset 1") {
    val dir = -45
    val dist = 10
    val r = retinasFactory.ball(1, 5)
    val sens = ballSens(dir, dist)
    val a = Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    r.see(sens, a)

    val v = 9 * 0.5

    a(0) mustBe 0.0 +- tolerance
    a(1) mustBe v +- tolerance
    a(2) mustBe 0.0 +- tolerance
    a(3) mustBe 0.0 +- tolerance
    a(4) mustBe 0.0 +- tolerance
    a(5) mustBe 0.0 +- tolerance
  }

  test("offset 3") {
    val dir = -45
    val dist = 10
    val r = retinasFactory.ball(3, 5)
    val sens = ballSens(dir, dist)
    val a = Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    r.see(sens, a)

    val v = 9 * 0.5

    a(0) mustBe 0.0 +- tolerance
    a(1) mustBe 0.0 +- tolerance
    a(2) mustBe 0.0 +- tolerance
    a(3) mustBe v +- tolerance
    a(4) mustBe 0.0 +- tolerance
    a(5) mustBe 0.0 +- tolerance
    a(6) mustBe 0.0 +- tolerance
    a(7) mustBe 0.0 +- tolerance
    a(8) mustBe 0.0 +- tolerance
    a(9) mustBe 0.0 +- tolerance
    a(10) mustBe 0.0 +- tolerance
    a(11) mustBe 0.0 +- tolerance
  }

  for ((dir, act) <- data) {
    test(s"ball near $dir") {
      val actNear = act.map(9 * _)
      val r = retinasFactory.ball(0, 5)
      val a = Array(0.0, 0.0, 0.0, 0.0, 0.0)
      r.see(ballSens(dir, 10), a)
      for(i <- a.indices) a(i) mustBe actNear(i) +- tolerance
    }
  }

  for ((dir, act) <- data) {
    test(s"ball mid $dir") {
      val actMid = act.map(7 * _)
      val r = retinasFactory.ball(0, 5)
      val a = Array(0.0, 0.0, 0.0, 0.0, 0.0)
      r.see(ballSens(dir, 30), a)
      for(i <- a.indices) a(i) mustBe actMid(i) +- tolerance
    }
  }


  for ((dir, act) <- data) {
    test(s"ball far $dir") {
      val actFar = act.map(5 * _)
      val r = retinasFactory.ball(0, 5)
      val a = Array(0.0, 0.0, 0.0, 0.0, 0.0)
      r.see(ballSens(dir, 50), a)
      for(i <- a.indices) a(i) mustBe actFar(i) +- tolerance
    }
  }

  for ((dir, act) <- data) {
    test(s"ball far 05 $dir") {
      val actFar = act.map(2.5 * _)
      val r = retinasFactory05.ball(0, 5)
      val a = Array(0.0, 0.0, 0.0, 0.0, 0.0)
      r.see(ballSens(dir, 50), a)
      for(i <- a.indices) a(i) mustBe actFar(i) +- tolerance
    }
  }

  // linpeak center: 0 width: 2
  val dataA: Seq[(Double, Double)] = Seq(
    (-2.0, 0.0),
    (-1.5, 0.0),
    (-1, 0.0),
    (-0.1, 0.9),
    (-0.2, 0.8),
    (-0.5, 0.5),
    (-0.9, 0.1),
    (0.0, 1.0),
    (0.1, 0.9),
    (0.5, 0.5),
    (0.9, 0.1),
    (1.0, 0.0),
    (2.0, 0.0),
    (1000.0, 0.0),
  )
  for ((x, y) <- dataA) {
    val f = Functions.linpeak(0.0, 2.0)(_)
    val xf = fmt(x)
    val yf = fmt(y)
    test(s"function linpeak A $xf $yf") {
      f(x) mustBe y +- 0.01
    }
  }

  // linpeak center: 0.5 width: 1.5
  val dataB: Seq[(Double, Double)] = Seq(
    (-2.0, 0.0),
    (-1.5, 0.0),
    (-1, 0.0),
    (-0.9, 0.06666),
    (-0.1, 0.6),
    (-0.25, 0.5),
    (0.0, 0.6666),
    (0.1, 0.7333),
    (0.5, 1.0),
    (0.6, 0.93333),
    (0.75, 0.8333),
    (0.9, 0.73333),
    (1.0, 0.66666),
    (1.25, 0.5),
    (1.5, 0.33333),
    (2.0, 0.0),
    (1000.0, 0.0),
  )
  for ((x, y) <- dataB) {
    val f = Functions.linpeak(0.5, 3)(_)
    val xf = fmt(x)
    val yf = fmt(y)
    test(s"function linpeak B $xf $yf") {
      f(x) mustBe y +- 0.01
    }
  }



  private def ballSens(dir: Double, dist: Double): Sensors = {
    val ddv: DistDirVision = new DistDirVision()
    ddv.setDirection(dir)
    ddv.setDistance(dist)
    val s = new Sensors
    s.setBall(ddv)
    s
  }


  def fmt(v: Double): String = f"$v%.2f"

}

