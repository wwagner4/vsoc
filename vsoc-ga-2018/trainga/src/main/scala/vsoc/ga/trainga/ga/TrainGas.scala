package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.ga.impl.{OutputFactors, TrainGa01Abstract, TrainGaAbstract}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

object TrainGas {

  def trainGa01: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01_m1: TrainGa[Double] = new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override protected def fitnessDesc: String = "consider all"

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate S
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01_mS: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mS"

    override def mutationRate: Double = 0.0001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate S 0.0001
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01m50: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m50"

    override def mutationRate: Double = 0.005

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate M 0.005
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01m10: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m10"

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate M 0.005
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01ofM: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM"

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |output factors medium
         |$propertiesFmt
      """.stripMargin

    override def outputFactors: OutputFactors = OutputFactors(50.0, 50.0, 5.0)

  }

  def trainGa01ofS: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofS"

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |output factors small
         |$propertiesFmt
      """.stripMargin

    override def outputFactors: OutputFactors = OutputFactors(10.0, 10.0, 1.0)

  }

  def trainGa01m05: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m05"

    override def mutationRate: Double = 0.0005

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate M 0.005
         |$propertiesFmt
      """.stripMargin
  }




  def trainGa01_mM: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mM"

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate M 0.001
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa01_mL: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mL"

    override def mutationRate: Double = 0.01

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |mutation rate L 0.01
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

    override def popMultiplicationTestFactor: Int = 6

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |Smaller population size and more test matches
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa03: TrainGa[Double] = new TrainGaAbstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override protected def fitnessDesc: String = "consider all"

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def popMultiplicationTestFactor: Int = 12

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |Smaller population size and more test matches
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

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Optimize Ball Kicks. $id
         |
         |Fitnessfunction takes only the number of ball kicks to calculate the fitness of a team.
         |
         |$propertiesFmt
      """.stripMargin
  }

}
