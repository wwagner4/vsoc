package vsoc.ga.common

import java.nio.file.Paths

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.ga.common.persist.Persistors

class CommonTestSuite extends FunSuite with MustMatchers {

  test("persist") {

    val p  = Persistors.workDir
    val a = ToBePersisted("hello", List(0.0, 0.2, -0.5))

    p.save(Paths.get(".test", "01.ser")) {
      oos => oos.writeObject(a)
    }

    val b = p.load(Paths.get(".test", "01.ser")) {
      ois => ois.readObject().asInstanceOf[ToBePersisted]
    }

    b.a mustBe "hello"
    b.b.size mustBe 3
    b.b(0) mustBe 0.0 +- 0.001
    b.b(1) mustBe 0.2 +- 0.001
    b.b(2) mustBe -0.5 +- 0.001
  }


}

