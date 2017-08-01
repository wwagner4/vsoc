package vsoc.training

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.scalatest.FunSuite

class UtilTrainingSuite extends FunSuite {

  test("convert to xyz 01") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8),
      Array(1.2, 2.7, 3.9),
      Array(1.3, 2.6, 3.0),
      Array(1.4, 2.5, 3.2)
    ))
    println(array)
    val x = array.getColumns(0).data.asDouble().toList
    val y = array.getColumns(1).data.asDouble().toList
    val z = array.getColumns(2).data.asDouble().toList
    println(x)
    println(y)
    println(z)

    val zipped = x.zip(y.zip(z))

    val trip = zipped.map{case (a, (b, c)) => (a, b, c)}
    println(trip)
  }

  test("convert to xyz 02") {
    val array = Nd4j.create(Array(
      Array(1.0, 2.0, 3.8),
      Array(1.2, 2.7, 3.9),
      Array(1.3, 2.6, 3.0),
      Array(1.4, 2.5, 3.2)
    ))
    val a1: INDArray = array.getColumns(0, 1)
    for (i <- 0 until 4; j <- 0 until 2) yield {

    }

  }

}