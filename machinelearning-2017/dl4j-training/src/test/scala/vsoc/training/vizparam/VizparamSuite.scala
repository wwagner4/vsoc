package vsoc.training.vizparam

import org.scalatest.{FunSuite, MustMatchers}
import vsoc.training.MetaParam

class VizparamSuite extends FunSuite with MustMatchers {

  import Vizparam.PropsManager._

  val mp = MetaParam(
    description = "test",
    variableParmDescription = () => "vd")

  test("to pairs size") {
    val props = toProps(mp)
    props.size mustEqual 6
  }

  test("to pairs learning rate 0.001") {
    val props = toProps(mp)
    value(props, "learningRate") mustEqual "1.0E-03"
  }

  test("to pairs learning rate 0.00045") {
    val props = toProps(mp.copy(learningRate = 0.00045))
    value(props, "learningRate") mustEqual "4.5E-04"
  }

  test("to pairs training data") {
    val props = toProps(mp)
    value(props, "trainingData") mustEqual "playerpos_x A 500000"
  }

  test("to pairs batchSizeTrainingDataRelative") {
    val props = toProps(mp)
    value(props, "batchSizeTrainingDataRelative") mustEqual "0.80"
  }

  test("to pairs testData") {
    val props = toProps(mp)
    value(props, "testData") mustEqual "playerpos_x B 1000"
  }

  test("to pairs iterations") {
    val props = toProps(mp)
    value(props, "iterations") mustEqual "3"
  }

  test("to pairs seed") {
    val props = toProps(mp)
    value(props, "seed") mustEqual "1"
  }

  test("reduce empty list") {
    an [IllegalStateException] must be thrownBy reduce(List.empty[Seq[(String, String)]])
  }

  test("reduce list with one elem") {
    val propsList: Seq[Seq[(String, String)]] = List(toProps(mp))
    val collected = reduce(propsList)
    for (((key, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be (Seq("1.0E-03"))
        case 1 => values must be (Seq("playerpos_x A 500000"))
        case 2 => values must be (Seq("0.80"))
        case 3 => values must be (Seq("playerpos_x B 1000"))
        case 4 => values must be (Seq("3"))
        case 5 => values must be (Seq("1"))
      }
    }
  }

  test("reduce list with two equal elems") {
    val props1: Seq[(String, String)] = toProps(mp)
    val props2: Seq[(String, String)] = toProps(mp)
    val propsList = List(props1, props2)
    val collected = reduce(propsList)
    for (((key, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be (Seq("1.0E-03"))
        case 1 => values must be (Seq("playerpos_x A 500000"))
        case 2 => values must be (Seq("0.80"))
        case 3 => values must be (Seq("playerpos_x B 1000"))
        case 4 => values must be (Seq("3"))
        case 5 => values must be (Seq("1"))
      }
    }
  }

  test("reduce list with two non equal elems") {
    val props1: Seq[(String, String)] = toProps(mp)
    val props2: Seq[(String, String)] = toProps(mp.copy(learningRate = 0.00045))
    val propsList = List(props1, props2)
    val collected = reduce(propsList)
    for (((key, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be (Seq("1.0E-03", "4.5E-04"))
        case 1 => values must be (Seq("playerpos_x A 500000"))
        case 2 => values must be (Seq("0.80"))
        case 3 => values must be (Seq("playerpos_x B 1000"))
        case 4 => values must be (Seq("3"))
        case 5 => values must be (Seq("1"))
      }
    }
  }

  def value(props: Seq[(String, String)], key: String): String = {
    val (_, _value) = props.filter {
      case (_key, _) if _key == key => true
      case _ => false
    }(0)
    _value
  }

}
