package vsoc.ga.common

import java.nio.file.{Path, Paths}

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.common.persist.Persistors

class CommonTestSuite extends FunSuite with MustMatchers {

  private val testDir = Paths.get(System.getProperty("user.home"), ".test")

  def filePath(dirRel: String, fname: String): Path =
    testDir.resolve(Paths.get(dirRel, fname))

  test("persist") {

    val p  = Persistors.nio
    val a = ToBePersisted("hello", List(0.0, 0.2, -0.5))

    p.save(filePath(".test", "01.ser")) {
      oos => oos.writeObject(a)
    }

    val file = filePath(".test", "01.ser")
    val b = p.load(file) {
      ois => ois.readObject().asInstanceOf[ToBePersisted]
    }

    val c = p.load(filePath(".test", "02.ser")) {
      ois => ois.readObject().asInstanceOf[ToBePersisted]
    }

    c.isDefined mustBe false

    b.isDefined mustBe true
    b.get.a mustBe "hello"
    b.get.b.size mustBe 3
    b.get.b(0) mustBe 0.0 +- 0.001
    b.get.b(1) mustBe 0.2 +- 0.001
    b.get.b(2) mustBe -0.5 +- 0.001
  }

  test("transform asArray") {
    import UtilTransform._

    val s = Seq(Seq(1.0, 2.0, -3.0), Seq(5.0), Seq())
    val a = asArray(s)

    a.length mustBe 3
    a(0).length mustBe 3
    a(1).length mustBe 1
    a(2).length mustBe 0

    a(0)(0) mustBe 1.0 +- 0.0001
    a(0)(1) mustBe 2.0 +- 0.0001
    a(0)(2) mustBe -3.0 +- 0.0001

    a(1)(0) mustBe 5.0 +- 0.0001

  }

  test("transform toSeq") {
    import UtilTransform._

    val s = Seq(Seq(1.0, 2.0, -3.0), Seq(5.0), Seq())
    val a = asArray(s)
    val b = toSeq(a)

    b.length mustBe 3
    b(0).length mustBe 3
    b(1).length mustBe 1
    b(2).length mustBe 0

    b(0)(0) mustBe 1.0 +- 0.0001
    b(0)(1) mustBe 2.0 +- 0.0001
    b(0)(2) mustBe -3.0 +- 0.0001

    b(1)(0) mustBe 5.0 +- 0.0001

  }

  test("persist seq of double") {
    import UtilTransform._

    val p  = Persistors.nio

    val s = Seq(Seq(1.0, 2.0, -3.0), Seq(5.0), Seq())
    val a = asArray(s)

    val file = filePath("test", "seq.ser")
    p.save(file)(s => s.writeObject(a))

    val a1 = p.load(file)(s => s.readObject().asInstanceOf[Array[Array[Double]]])
    val b = toSeq(a1.get)

    b.length mustBe 3
    b(0).length mustBe 3
    b(1).length mustBe 1
    b(2).length mustBe 0

    b(0)(0) mustBe 1.0 +- 0.0001
    b(0)(1) mustBe 2.0 +- 0.0001
    b(0)(2) mustBe -3.0 +- 0.0001

    b(1)(0) mustBe 5.0 +- 0.0001

  }

}

