package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.ga.impl.TrainGaAbstract
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

object TrainGas {

  def trainGa01: TrainGa =  new TrainGaAbstract {

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override def ran: Random = Random

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  }

  def trainGaKicks01: TrainGa =  new TrainGaAbstract {

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessKicks01

    override def ran: Random = Random

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  }

}
