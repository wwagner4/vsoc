package vsoc.ga.trainga.ga

import vsoc.ga.trainga.behav.OutputMapperNn
import vsoc.ga.trainga.ga.impl._
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

import scala.util.Random

object TrainGas {

  def trainGa01: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "First try"
  }

  def trainGa01_m1: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01"

    override def fitness: FitnessFunction = FitnessFunctions.fitnessConsiderAll01

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Test mutation rate"
  }

  def trainGa01_mS: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mS"

    override def mutationRate: Double = 0.0001

    override def fullDescHeading: String = "Test mutation rate"
  }

  def trainGa01m50: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m50"

    override def mutationRate: Double = 0.005

    override def fullDescHeading: String = "Test mutation rate"
  }

  def trainGa01m10: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m10"

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Test mutation rate"
  }

  def trainGa01ofM: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM"

    override def outputFactors: OutputFactors = OutputFactors(50.0, 50.0, 5.0)

    override def fullDescHeading: String = "Test mutation rate"
  }

  def trainGa01ofM1: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM1"

    override def outputFactors: OutputFactors = OutputFactors(50.0, 50.0, 5.0)

    override def fullDescHeading: String = "Test output factors"

  }

  def trainGa01ofM2: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofM2"

    override def outputFactors: OutputFactors = OutputFactors(50.0, 20.0, 5.0)

    override def fullDescHeading: String = "Test output factors"

  }

  def trainGa01ofS: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01ofS"

    override def outputFactors: OutputFactors = OutputFactors(10.0, 10.0, 1.0)

    override def fullDescHeading: String = "Test output factors"

  }

  def trainGa01m05: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01m05"

    override def mutationRate: Double = 0.0005

    override def fullDescHeading: String = "Test mutation rate"

  }


  def trainGa01_mM: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mM"

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Test mutation rate"

  }

  def trainGa01_mL: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa01_mL"

    override def mutationRate: Double = 0.01

    override def fullDescHeading: String = "Test mutation rate"

  }

  def trainGa02: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def popMultiplicationTestFactor: Int = 6

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Small population size"
  }

  def trainGa03: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGa02"

    override def popMultiplicationTestFactor: Int = 12

    override def populationSize: Int = 10

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Small population size and more testing"

  }

  def trainGaKicks01: TrainGa[Double] = new TrainGa01Abstract {

    // Must be equal to the constructing method to ensure correct persistence
    override def id: String = "trainGaKicks01"

    override def fitness: FitnessFunction = FitnessFunctions.fitnessKicks01

    override def ran: Random = Random.javaRandomToRandom(new java.util.Random())

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

    override def popMultiplicationTestFactor: Int = 3

    override def populationSize: Int = 20

    override def mutationRate: Double = 0.001

    override def fullDescHeading: String = "Consider only kicking"

  }

  def trainGa04K0: TrainGa[Double] = new TrainGa04Abstract {

    override def fitness: FitnessFunction = FitnessFunctions.fitnessConsiderAll01K0

    override def id: String = "trainGa04K0"

    override def outMapper: OutputMapperNn = OutputMappers.om01FDefault

    override def fullDescHeading: String = "Consider only kicking. Kick out penalty small"

  }

  def trainGa04G0: TrainGa[Double] = new TrainGa04Abstract {

    override def fitness: FitnessFunction = FitnessFunctions.fitnessConsiderAll01G0

    override def id: String = "trainGa04G0"

    override def outMapper: OutputMapperNn = OutputMappers.om01FDefault

    override def fullDescHeading: String = "Consider mostly goals"

  }

  def trainGa04M0: TrainGa[Double] = new TrainGa04Abstract {

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override def id: String = "trainGa04M0"

    override def outMapper: OutputMapperNn = OutputMappers.om01FDefault

    override def fullDescHeading: String = "Deeper neural net. 2 layers"
  }

  def trainGa04M0om02: TrainGa[Double] = new TrainGa04Abstract {

    override def id: String = "trainGa04M0om02"

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override def outMapper: OutputMapperNn = OutputMappers.om02

    override def fullDescHeading: String = "Deeper neural net. 2 layers and advanced output mapping"

  }

  def trainGa04M0om02varL: TrainGa[Double] = new TrainGa04Abstract {

    override def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override def outMapper: OutputMapperNn = OutputMappers.om02varL

    override def id: String = "trainGa04M0om02varL"

    override def fullDescHeading: String = "Deeper neural net. 2 layers and advanced output mapping with greater variance"

  }

  def trainGa05fitFac01: TrainGa[Double] = new TrainGa05Abstract {

    override def id: String = "trainGa05fitFac01"

    override protected def fitness: FitnessFunction = FitnessFunctions.fitnessFactor01

    override def fullDescHeading: String = "Test fitness function"
  }

  def trainGa05fitFac02: TrainGa[Double] = new TrainGa05Abstract {

    override def id: String = "trainGa05fitFac02"

    override protected def fitness: FitnessFunction = FitnessFunctions.fitnessFactor02

    override def fullDescHeading: String = "Test fitness function"
  }

  def trainGa05fitFac03: TrainGa[Double] = new TrainGa05Abstract {

    override def id: String = "trainGa05fitFac03"

    override protected def fitness: FitnessFunction = FitnessFunctions.fitnessFactor03

    override def fullDescHeading: String = "Test fitness function"
  }

}
