package vsoc.ga.common.describe

import org.scalatest.{FunSuite, MustMatchers}

class DescribableSuite extends FunSuite with MustMatchers {

  test("tryout DA") {
    val a = DA("quite a long string", 2.55)
    val afmt = DescribableFormatter.format(a, 0)
    println(afmt)
  }

  test("tryout DB") {
    val a = DB(5.1e22)
    val afmt = DescribableFormatter.format(a, 0)
    println(afmt)
  }

}

