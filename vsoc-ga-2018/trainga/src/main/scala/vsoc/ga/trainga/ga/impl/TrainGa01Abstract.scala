package vsoc.ga.trainga.ga.impl

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.ga.FitnessFunctions
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

abstract class TrainGa01Abstract extends TrainGaAbstract {

  override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

  override protected def fitnessDesc: String = "consider all"

  override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  override def popMultiplicationTestFactor: Int = 3

  override def populationSize: Int = 20


}
