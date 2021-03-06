package vsoc.ga.trainga.ga

import vsoc.ga.matches.behav.{InputMapperNn, InputMappers, OutputMapperNn, OutputMappers}
import vsoc.ga.matches.nn.{NeuralNet, NeuralNets}
import vsoc.ga.trainga.ga.impl.player01._
import vsoc.ga.trainga.ga.impl.team01._

object TrainGas {


  def trainGaB01: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB01"

    override protected def fitness: TrainGaFitnessFunction[Data02] = FitnessFunctions.data02A01

    override def fullDesc: String = "B initial test"

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = InputMappers.default

    override protected def outMapper: OutputMapperNn = OutputMappers.om02
  }

  def trainGaB02: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB02"

    override protected def fitness: TrainGaFitnessFunction[Data02] = FitnessFunctions.data02A02

    override def fullDesc: String =
      """B first improvement
        |Tryout better Fitness function
      """.stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = InputMappers.default

    override protected def outMapper: OutputMapperNn = OutputMappers.om02
  }

  def trainGaB03: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB03"

    override protected def fitness: TrainGaFitnessFunction[Data02] = FitnessFunctions.data02A03

    override def fullDesc: String =
      """More training matches by increasing the test factor (2 -> 4)
        |New fitness function A03
      """.stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = InputMappers.default

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

  def trainGaB04: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB04"

    override protected def fitness: TrainGaFitnessFunction[Data02] = FitnessFunctions.data02A04

    override def fullDesc: String =
      """New fitness function data02A04"""

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = InputMappers.default

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

  def trainGaB05: TrainGa[Data02] = new TrainGaAbstract {

    override def id: String = "trainGaB05"

    override protected def fitness: TrainGaFitnessFunction[Data02] = FitnessFunctions.data02A05

    override def fullDesc: String =
      """Iteration 6
        |New fitness function data02A05""".stripMargin

    override protected def createNeuralNet: () => NeuralNet = () => NeuralNets.team02

    override protected def inMapper: InputMapperNn = InputMappers.default

    override protected def outMapper: OutputMapperNn = OutputMappers.om02

    override def testFactor: Int = 4

  }

  def trainGaPlayer01Simple: TrainGa[DataPlayer01] = new TrainGaPlayer01A

  def trainGaPlayer01A: TrainGa[DataPlayer01] = new TrainGaPlayer01A

  def trainGaPlayer01B: TrainGa[DataPlayer01] = new TrainGaPlayer01B

  def trainGaPlayer01C: TrainGa[DataPlayer01] = new TrainGaPlayer01C

  def trainGaPlayer01CRec01: TrainGa[DataPlayer01] = new TrainGaPlayer01CRec01

}
