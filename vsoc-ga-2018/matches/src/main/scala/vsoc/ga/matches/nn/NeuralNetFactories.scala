package vsoc.ga.matches.nn

import scala.util.Random

object NeuralNetFactories {


  private def randomAlleleDefault(ran: Random): Double = 2.0 * ran.nextDouble() - 1.0

  def default: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.default

    override def parameterSize: Int = 63

    override def inputSize: Int = 2

    override def outputSize: Int = 3

    override def randomAllele(ran: Random): Double = randomAlleleDefault(ran)
  }

  def test: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.test

    override def parameterSize: Int = 63

    override def inputSize: Int = 2

    override def outputSize: Int = 3

    override def randomAllele(ran: Random): Double = randomAlleleDefault(ran)
  }

  def team01: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.team01

    override def parameterSize: Int = 44404

    override def inputSize: Int = 140

    override def outputSize: Int = 4

    override def randomAllele(ran: Random): Double = randomAlleleDefault(ran)
  }

  def team02: NeuralNetFactory = new NeuralNetFactory {
    override def neuralNet: NeuralNet = NeuralNets.team02

    override def parameterSize: Int = 19704

    override def inputSize: Int = 140

    override def outputSize: Int = 4

    override def randomAllele(ran: Random): Double = randomAlleleDefault(ran)
  }

  def rnn01: NeuralNetFactory =

    new NeuralNetFactory {
      override def neuralNet: NeuralNet = NeuralNets.rnn01

      override def parameterSize: Int = 19704

      override def inputSize: Int = 140

      override def outputSize: Int = 4

      override def randomAllele(ran: Random): Double = ran.nextDouble()
    }
}
