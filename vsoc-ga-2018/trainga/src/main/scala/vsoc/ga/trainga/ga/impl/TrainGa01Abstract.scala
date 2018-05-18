package vsoc.ga.trainga.ga.impl

import vsoc.ga.matches.TeamResult
import vsoc.ga.trainga.behav.{InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.ga.{FitnessFunctions, OutputFactors, OutputMappers}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

abstract class TrainGa01Abstract extends TrainGaAbstract {

  override def fitness: TeamResult => Double = FitnessFunctions.fitnessConsiderAll01

  override def fitnessDesc: String = "consider all"

  override def createNeuralNet: () => NeuralNet = () => NeuralNets.team01

  protected def outputFactors: OutputFactors = OutputFactors()

  override def inMapper: InputMapperNn = new InputMapperNnTeam(1.0)

  override def outMapper: OutputMapperNn = OutputMappers.om01F(outputFactors)

  override def properties: Seq[(String, Any)] = super.properties ++ Seq(
    ("output mapper", "'om01F' primitive first trial"),
    ("output factors", outputFactors.toString)
  )

}
