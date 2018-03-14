package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.ga.impl.TrainGaAbstract
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

object TrainGas {

  def trainGa01: TrainGa[Double] = new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override protected def fitnessDesc: String = "consider all"

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def shortDesc: String = "fitness consider all"

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |
        |$propertiesFmt
      """.stripMargin

  }

  def trainGa02: TrainGa[Double] = new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override protected def fitnessDesc: String = "consider all"

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def shortDesc: String = "fitness consider all"

    override def popMultiplicationTestFactor: Int = 6

    override def populationSize: Int = 10

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |Smaller population size and more test matches
         |
        |$propertiesFmt
      """.stripMargin

  }

  def trainGaKicks01: TrainGa[Double] = new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGaKicks01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessKicks01

    override protected def fitnessDesc: String = "consider kicks"

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def shortDesc: String = "fitness from kicks"

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def fullDesc: String =
      s"""Optimize Ball Kicks. $id
         |
         |Fitnessfunction takes only the number of ball kicks to calculate the fitness of a team.
         |
         |$propertiesFmt
      """.stripMargin
  }

}
