package vsoc.ga.trainga

import java.nio.file.{Files, Paths}

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNetPersist, NeuralNets}
import vsoc.ga.trainga.reflection.UtilReflection

class CampsSuite extends FunSuite with MustMatchers {

  test("Wrapper get param") {
    val nn = NeuralNets.test
    val p = nn.getParam
    p.length mustBe 63
  }

  test("Wrapper set param wrong size") {
    val p = Array(2.3, 1.2, 3.4, 5.6)
    val nn = NeuralNets.test
    an[IllegalStateException] must be thrownBy nn.setParam(p)
  }

  test("Wrapper set param right size") {
    val p = Stream.iterate(0.0)(a => a + 1.0).take(63).toArray
    val nn = NeuralNets.test
    nn.setParam(p)
    val p1 = nn.getParam
    p1.length mustBe p.length
    p1.map(f).mkString(" ") mustBe p.map(f).mkString(" ")
  }

  test("Wrapper set param and output") {
    val p1 = Stream.iterate(0.0)(a => a + 1.0).take(63).toArray
    val p2 = Stream.iterate(3.0)(a => a + 1.0).take(63).toArray
    val nn = NeuralNets.test
    val in = Array(1.0, 2.0)

    nn.setParam(p1)
    val out1 = nn.output(in)

    nn.setParam(p2)
    val out2 = nn.output(in)
    out2.map(f).mkString(" ") must not be out1.map(f).mkString(" ")

    nn.setParam(p1)
    val out3 = nn.output(in)
    out3.map(f).mkString(" ") mustBe out1.map(f).mkString(" ")

  }

  test("reflection load net") {
    val nn = UtilReflection.call(NeuralNets, "default", classOf[NeuralNet])
    nn.getParam.length mustBe 63
  }

  test("save load neural net") {
    val nn = NeuralNets.test
    val params = Stream.continually(1.0).take(63).toArray
    nn.setParam(params)

    val dir = Paths.get(System.getProperty("user.home"), "work", "vsoc", "tmp")
    Files.createDirectories(dir)

    NeuralNetPersist.save(nn, dir.resolve("nn.ser"))
    val nn1 = NeuralNetPersist.load(dir.resolve("nn.ser"))

    nn1.getParam.map(f).mkString(" ") mustBe params.map(f).mkString(" ")
  }

  def f(d: Double): String = f"$d%2.0f"
}


