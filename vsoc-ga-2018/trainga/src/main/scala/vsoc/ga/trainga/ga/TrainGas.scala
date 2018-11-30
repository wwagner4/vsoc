package vsoc.ga.trainga.ga

import vsoc.ga.trainga.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.ga.impl._
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

object TrainGas {


  def trainGaB01: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB01"

    override protected def fitness: FitnessFunction[Data02] = FitnessFunctions.data02A01

    override def fullDescHeading: String = "B initial test"

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

    override protected def outMapper: OutputMapperNn = OutputMappers.om02
  }

  def trainGaB02: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB02"

    override protected def fitness: FitnessFunction[Data02] = FitnessFunctions.data02A02

    override def fullDescHeading: String =
      """B first improvement
        |Tryout better Fitness function
      """.stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

    override protected def outMapper: OutputMapperNn = OutputMappers.om02
  }

  def trainGaB03: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB03"

    override protected def fitness: FitnessFunction[Data02] = FitnessFunctions.data02A03

    override def fullDescHeading: String =
      """More training matches by increasing the test factor (2 -> 4)
        |New fitness function A03
      """.stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

  def trainGaB04: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB04"

    override protected def fitness: FitnessFunction[Data02] = FitnessFunctions.data02A04

    override def fullDescHeading: String =
      """New fitness function data02A04"""

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

  def trainGaB05: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB05"

    override protected def fitness: FitnessFunction[Data02] = FitnessFunctions.data02A05

    override def fullDescHeading: String =
      """Iteration 6
        |New fitness function data02A05""".stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

}
