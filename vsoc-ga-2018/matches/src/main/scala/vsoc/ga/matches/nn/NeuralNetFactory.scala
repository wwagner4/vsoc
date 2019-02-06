package vsoc.ga.matches.nn

import scala.util.Random

trait NeuralNetFactory {

  def neuralNet: NeuralNet

  def parameterSize: Int

  def inputSize: Int

  def outputSize: Int

  def randomAllele(ran: Random): Double


}
