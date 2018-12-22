package vsoc.ga.matches.nn

trait NeuralNetFactory {

  def neuralNet: NeuralNet

  def parameterSize: Int

  def inputSize: Int

  def outputSize: Int

}
