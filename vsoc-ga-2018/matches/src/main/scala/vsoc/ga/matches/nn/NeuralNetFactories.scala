package vsoc.ga.matches.nn

object NeuralNetFactories {

  def default: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.default

    override def parameterSize: Int = 63

    override def inputSize: Int = 2

    override def outputSize: Int = 3
  }

  def test: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.test

    override def parameterSize: Int = 63

    override def inputSize: Int = 2

    override def outputSize: Int = 3
  }

  def team01: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.team01

    override def parameterSize: Int = 44404

    override def inputSize: Int = 140

    override def outputSize: Int = 4
  }

  def team02: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.team02

    override def parameterSize: Int = 19704

    override def inputSize: Int = 140

    override def outputSize: Int = 4
  }

  def rnn01: NeuralNetFactory =

    new NeuralNetFactory {
      override def neuralNet: NeuralNet = NeuralNets.rnn01

      override def parameterSize: Int = 19704

      override def inputSize: Int = 140

      override def outputSize: Int = 4
    }
}
