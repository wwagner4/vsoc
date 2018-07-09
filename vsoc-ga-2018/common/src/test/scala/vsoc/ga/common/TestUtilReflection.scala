package vsoc.ga.common

import org.scalatest.{FunSuite, MustMatchers}

class TestUtilReflection extends FunSuite with MustMatchers {

  test("names Data01"){
    val data = TestData01("A", 2.4, 5)
    UtilReflection.allPropNames(data) mustBe Seq("s", "d", "i")
  }

  test("values Data01"){
    val data = TestData01("A", 2.4, 5)
    val expected: Seq[Any] = Seq("A", 2.4, 5)
    UtilReflection.allPropValues(data) mustBe expected
  }

  test("names Data02"){
    val data = TestData02(2.4, "A", 5)
    UtilReflection.allPropNames(data) mustBe Seq("d", "s", "i")
  }

  test("values Data02"){
    val data = TestData02(2.4, "A", 5)
    val expected: Seq[Any] = Seq(2.4, "A", 5)
    UtilReflection.allPropValues(data) mustBe expected
  }

}
