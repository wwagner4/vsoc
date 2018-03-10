package vsoc.ga.trainga.nn

import vsoc.ga.common.describe.Describable

trait NeuralNet extends Describable {

  def id: String

  def output(in: Array[Double]): Array[Double]

  def setParam(p: Array[Double]): Unit

  def getParam: Array[Double]

}
