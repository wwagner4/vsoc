package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.ga.impl.TrainGaAbstract
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

object TrainGas {

  def trainGa01: TrainGa[Double] =  new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01
  }

  def trainGaKicks01: TrainGa[Double] =  new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGaKicks01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessKicks01

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  }

}
