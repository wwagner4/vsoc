package machinelearning

import org.scalatest.FunSuite
import breeze.linalg._

class BreezeSuite extends FunSuite {

  ignore("performance inverse matrix") {
    List(5, 5, 10, 100, 200, 500, 1000, 2000, 5000).foreach{n =>
      val y = DenseMatrix.rand[Double](n, n)

      val t1 = System.currentTimeMillis()
      inv(y)
      val t2 = System.currentTimeMillis()

      println("inv %5d x %5d  %10d milis" format(n, n, t2 - t1))
    }
  }

  test("vector operations") {
    println("--- VECTOR OPERATIONS -------------------------------------")

    val x: Vector[Double] = Vector(1.0, 2.0, 3.0)
    println("x   : %s" format x)
    println("x'  : %s" format x.t)

    val x0 = x(0)
    println(f"x1   : $x0%.2f")
    val x1 = x(1)
    println(f"x2   : $x1%.2f")
    val x2 = x(2)
    println(f"x3   : $x2%.2f")

    val y = x.t * x
    println("x' * x : %s" format y)

    val s = x + x
    println("x + x : %s" format s)

    val s1 = sum(x)
    println("sum(x) : %s" format s1)

  }

  test("vector matrix operations") {
    println("--- VECTOR MATRIX OPERATIONS -------------------------------------")

    val x: Vector[Double] = Vector(1.0, 2.0, 3.0)
    val M: Matrix[Double] = Matrix((1.0, 2.0, 3.0), (2.0, 3.0, 4.0), (3.0, 4.0, 5.0))

    println("M: %s" format M)

    val M1 = M.t
    println("M': %s" format M1)

    val y0 =  M * x
    println("M * x : %s" format y0)
  }

  test("convert vector matrix") {
    println("--- CONVERT VECTOR MATRIX -------------------------------------")

    val x: Vector[Double] = Vector(1.0, 2.0, 3.0)
    println("x: %s" format x)

    val M = x.asInstanceOf[DenseVector[Double]].asDenseMatrix
    println("M: %s" format M)


    val x1 = M(::,0)
    println("x1: %s" format x1)

    val x2 = M.t(::,0)
    println("x2: %s" format x2)
  }

  test("matrix matrix operations") {
    println("--- MATRIX MATRIX OPERATIONS -------------------------------------")
    // M 3 x 2
    val M1 = Matrix((1.0, 2.0), (2.0, 3.0), (3.0, 4.0))
    println("M1:\n" + M1)
    println(s"M1 ${M1.rows} x ${M1.cols}")

    // M 2 x 3
    val M2 = DenseMatrix((1.0, 2.0, 4.0), (2.0, 3.0, 5.0))
    println("M2:\n" + M2)
    println(s"M2 ${M2.rows} x ${M2.cols}")

    // M 2 x 1
    val vm = DenseMatrix(1.0, 2.0)
    println("vm:\n" + vm)
    println(s"vm ${vm.rows} x ${vm.cols}")

    // v 2 x 1
    val vv = DenseVector(1.0, 2.0)
    println("vv:\n" + vv)
    println(s"vv ${vv.length} x 1")

    // 3 x 2   *   2 x 1
    val a = M1 * vm

    // 3 x 2   *   2 x 1
    val b = M1 * vv

    val c = M2.t * vm

    val d = M2.t * vv


    println("a:\n" + a)
    println("b:\n" + b)
    println("c:\n" + c)
    println("d:\n" + d)

  }

}
