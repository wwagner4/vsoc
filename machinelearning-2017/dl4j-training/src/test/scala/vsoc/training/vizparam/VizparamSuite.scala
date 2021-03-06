package vsoc.training.vizparam

import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.scalatest.{FunSuite, MustMatchers}
import vsoc.common.Dat
import vsoc.training.{MetaParam, Regularisation}

//noinspection RedundantDefaultArgument
class VizparamSuite extends FunSuite with MustMatchers {

  import Vizparam.PropsManager._

  val mp = MetaParam(

    description = "test",
    learningRate = 0.0001,
    trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, Dat.Size_500000),
    batchSizeTrainingDataRelative = 0.1,
    testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000),
    iterations = 3,
    optAlgo = OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT,
    seed = 1L,
    variableParmDescription = () => "vd")

  test("to pairs size") {
    val props = toProps(mp)
    props.size mustEqual 9
  }

  test("to pairs learning rate 0.001") {
    val props = toProps(mp)
    value(props, "learningRate") mustEqual "1.0E-04"
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
    value(props, "batchSizeTrainingDataRelative") mustEqual "0.10"
  }

  test("to pairs testData") {
    val props = toProps(mp)
    value(props, "testData") mustEqual "playerpos_x B 1000"
  }

  test("to pairs iterations") {
    val props = toProps(mp)
    value(props, "iterations") mustEqual "3"
  }

  test("to pairs optAlgo") {
    val props = toProps(mp)
    value(props, "optAlgo") mustEqual "STOCHASTIC_GRADIENT_DESCENT"
  }

  test("to pairs seed") {
    val props = toProps(mp)
    value(props, "seed") mustEqual "1"
  }

  test("reduce empty list") {
    an[IllegalStateException] must be thrownBy reduce(List.empty[Seq[(String, String)]])
  }

  test("reduce list with one elem") {
    val propsList: Seq[Seq[(String, String)]] = List(toProps(mp))
    val collected = reduce(propsList)
    for (((_, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be(Seq("1.0E-04"))
        case 1 => values must be(Seq("playerpos_x A 500000"))
        case 2 => values must be(Seq("0.10"))
        case 3 => values must be(Seq("playerpos_x B 1000"))
        case 4 => values must be(Seq("3"))
        case 5 => values must be(Seq("STOCHASTIC_GRADIENT_DESCENT"))
        case 6 => values must be(Seq("50"))
        case 7 => values must be(Seq("None"))
        case 8 => values must be(Seq("1"))
      }
    }
  }

  test("reduce list with one elem regularisation") {
    val propsList: Seq[Seq[(String, String)]] = List(toProps(mp.copy(regularisation = Some(Regularisation(0.001, 0.000001, 0.001, 0.01)))))
    val collected = reduce(propsList)
    for (((_, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be(Seq("1.0E-04"))
        case 1 => values must be(Seq("playerpos_x A 500000"))
        case 2 => values must be(Seq("0.10"))
        case 3 => values must be(Seq("playerpos_x B 1000"))
        case 4 => values must be(Seq("3"))
        case 5 => values must be(Seq("STOCHASTIC_GRADIENT_DESCENT"))
        case 6 => values must be(Seq("50"))
        case 7 => values must be(Seq("Some(Regularisation(0.001,1.0E-6,0.001,0.01))"))
        case 8 => values must be(Seq("1"))
      }
    }
  }

  test("reduce list with two equal elems") {
    val props1: Seq[(String, String)] = toProps(mp)
    val props2: Seq[(String, String)] = toProps(mp)
    val propsList = List(props1, props2)
    val collected = reduce(propsList)
    for (((_, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be(Seq("1.0E-04"))
        case 1 => values must be(Seq("playerpos_x A 500000"))
        case 2 => values must be(Seq("0.10"))
        case 3 => values must be(Seq("playerpos_x B 1000"))
        case 4 => values must be(Seq("3"))
        case 5 => values must be(Seq("STOCHASTIC_GRADIENT_DESCENT"))
        case 6 => values must be(Seq("50"))
        case 7 => values must be(Seq("None"))
        case 8 => values must be(Seq("1"))
      }
    }
  }

  test("reduce list with two non equal elems") {
    val props1: Seq[(String, String)] = toProps(mp)
    val props2: Seq[(String, String)] = toProps(mp.copy(learningRate = 0.00045))
    val propsList = List(props1, props2)
    val collected = reduce(propsList)
    for (((_, values), i) <- collected.zipWithIndex) {
      i match {
        case 0 => values must be(Seq("1.0E-04", "4.5E-04"))
        case 1 => values must be(Seq("playerpos_x A 500000"))
        case 2 => values must be(Seq("0.10"))
        case 3 => values must be(Seq("playerpos_x B 1000"))
        case 4 => values must be(Seq("3"))
        case 5 => values must be(Seq("STOCHASTIC_GRADIENT_DESCENT"))
        case 6 => values must be(Seq("50"))
        case 7 => values must be(Seq("None"))
        case 8 => values must be(Seq("1"))
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
