package vsoc.ga.trainga.ga.impl

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.ga.FitnessFunctions
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

abstract class TrainGa01Abstract extends TrainGaAbstract {

  override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

  override def fitnessDesc: String = "consider all"

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  protected def outputFactors: OutputFactors = OutputFactors()

  override def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

  override def outMapper: OutputMapperNn = new OutputMapperNnTeam(outputFactors)

  override def fullDesc: String =
    """configurable output mapper
    """.stripMargin

  override def properties: Seq[(String, Any)] = Seq(
    ("output factors", outputFactors.toString)
  )

}
