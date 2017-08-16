package vsoc.training.vizparam

import org.scalatest.{FunSuite, MustMatchers, TestSuite}
import vsoc.common.{Dat, Formatter}
import vsoc.training.MetaParam

class VizparamSuite extends FunSuite with MustMatchers {

  val mp = MetaParam(variableParmDescription = () => "vd")

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

  test("to pairs size") {
    val props = toProps(mp)
    props.size mustEqual 6
  }

  def value(props: Seq[(String, String)], key: String): String = {
    val (_, _value) = props.filter {
      case (_key, _) if _key == key => true
      case _ => false
    }(0)
    _value
  }

  def toProps(mp: MetaParam): Seq[(String, String)] = Seq(
    ("learningRate", formatDoubleExp(mp.learningRate)),
    ("trainingData", formatDataDesc(mp.trainingData)),
    ("batchSizeTrainingDataRelative", ""),
    ("testData", ""),
    ("iterations", ""),
    ("seed", "")
  )

  def formatDouble(value: Double): String = {
    Formatter.formatNumber("%f", value)
  }

  def formatDoubleExp(value: Double): String = {
    Formatter.formatNumber("%.1E", value)
  }

  def formatDataDesc(value: Dat.DataDesc): String = {
    s"${value.data.code} ${value.id.code} ${value.size.size}"
  }

}
