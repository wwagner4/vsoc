package tryout

import breeze.linalg._
import breeze.numerics.sin

object BreezeTryoutMain extends App {

  // Use one of the methods below. e.g 'matrixMutable()'
  matrixMutable()

  def matrixMutable(): Unit = {
    println("BreezeTryoutMain::matrixMutable")
    val m = DenseMatrix((0.1, 3.2), (0.7, 3.1), (0.4, 5.2))
    println(m)
    val vecs = for (i <- 0 until m.cols) yield {
      val v = m(::, i)
      sin(v)
    }
    val x1 = DenseMatrix(vecs:_*)
    println("----- 1 -")
    println(x1)
    println(m)
    val x2 = sum(x1(*, ::))
    println("----- 2 -")
    println(x1)
    println(x2)
    println(m)
    // Conclusion: m seems not to be muted

  }

  def summarizeRowsOfMatrix(): Unit = {
    val m = DenseMatrix((0.1, 3.2), (0.7, 3.1), (0.4, 5.2))
    println(m)
    val ms = sum(m(*, ::))
    println(ms)
  }

  def splitVectorInSubvectors(): Unit = {
    val v = DenseVector(1.0, 2.0, 3.0, 4.0, -1.0, -.9)
    val splitVectors: Iterator[DenseVector[Double]] = v.data.grouped(3).map { d => DenseVector(d) }
    for (sub <- splitVectors) {
      println(s" - split vector $sub}")
    }
  }

  def createMatrixFromVectors(): Unit = {
    val v1 = DenseVector(1.2, 2.4, 1.1)
    val v2 = DenseVector(2.2, 2.4, 2.1)
    val m = DenseMatrix(v1, v2) // <- Vectors are the rows of the new matrix
    println(m)
  }

  def allColumnsOfMatrix(): Unit = {
    println("Breeze tryout :: all columns of matrix")
    val m = DenseMatrix((0.1, 3.2), (0.7, 3.1), (0.4, 5.2))
    for (i <- 0 until m.cols) {
      val c = m(0 until m.rows, i)
      println(c)
    }
  }

  def columnsOfMatrix(): Unit = {
    println("Breeze tryout :: column of matrix")
    val m = DenseMatrix((0.1, 3.2), (0.7, 3.1), (0.4, 5.2))
    println("---- m - all")
    println(m.getClass.getName)
    println(m)

    val m0 = m(::, 0)
    println("---- m0 - all")
    println(m0.getClass.getName)
    println(m0)

    val m1 = m(::, 1)
    println("---- m1 - all")
    println(m1.getClass.getName)
    println(m1)
  }
}