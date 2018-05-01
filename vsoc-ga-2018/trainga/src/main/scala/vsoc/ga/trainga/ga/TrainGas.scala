package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.behav.OutputMapperNn
import vsoc.ga.trainga.ga.impl._
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

  def trainGa01_m1: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

    override def fitnessDesc: String = "consider all"

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

  def trainGa01ofM1: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM1"

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |output factors medium
         |turn factor reduced
         |$propertiesFmt
      """.stripMargin

    override def outputFactors: OutputFactors = OutputFactors(50.0, 50.0, 5.0)

  }

  def trainGa01ofM2: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM2"

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |output factors medium
         |kick factor reduced
         |$propertiesFmt
      """.stripMargin

    override def outputFactors: OutputFactors = OutputFactors(50.0, 20.0, 5.0)

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

  def trainGa02: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def popMultiplicationTestFactor: Int = 6

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |Smaller population size and more test matches
         |$propertiesFmt
      """.stripMargin

  }

  def trainGa03: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def popMultiplicationTestFactor: Int = 12

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDesc: String =
      s"""Consider all match parameters. '$id'
         |Smaller population size and more test matches
         |$propertiesFmt
      """.stripMargin

  }

  def trainGaKicks01: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGaKicks01"

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessKicks01

    override def fitnessDesc: String = "consider kicks"

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

  def trainGa04K0: TrainGa[Double] = new TrainGa04Abstract {

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01K0

    override def fitnessDesc: String = "consider all kickOut penalty 5"

    override def id: String = "trainGa04K0"

    override def fullDesc: String =
      s"""${super.fullDesc}
         |Relation between kick- and kickoutfactor changed
         |10 / 2 -> 10 / 5 (FitnessFunctions.fitnessConsiderAll02)
      """.stripMargin

  }

  def trainGa04G0: TrainGa[Double] = new TrainGa04Abstract {

    override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01G0

    override def fitnessDesc: String = "consider all goal-reard/penalty 100 -> 10"

    override def id: String = "trainGa04G0"

    override def fullDesc: String =
      s"""${super.fullDesc}
         |reward/penalty for goal/ownGoal changed
         |100 -> 10  (FitnessFunctions.fitnessConsiderAll03)
      """.stripMargin

  }

  def trainGa04M0: TrainGa[Double] = new TrainGa04Abstract {

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override def id: String = "trainGa04M0"

    override def fullDesc: String =
      s"""${super.fullDesc}
         |use a network with two intermedate layers
         |NeuralNets.team02
      """.stripMargin

  }

  def trainGa04M0om02: TrainGa[Double] = new TrainGa04Abstract {

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override def outMapper: OutputMapperNn = OutputMappers.om02

    override def id: String = "trainGa04M0"

    override def fullDesc: String =
      s"""${super.fullDesc}
         |use a network with two intermedate layers
         |NeuralNets.team02
         |OutputMapper 0m02
      """.stripMargin

  }

}
