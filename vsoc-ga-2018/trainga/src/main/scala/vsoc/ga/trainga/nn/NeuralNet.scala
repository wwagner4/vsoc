package vsoc.ga.trainga.nn

trait NeuralNet {

  def id: String

  def output(in: Array[Double]): Array[Double]

  def setParam(p: Array[Double]): Unit

  def getParam: Array[Double]

}
