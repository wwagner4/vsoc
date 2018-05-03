package vsoc.ga.trainga.nn.analyse

import vsoc.ga.trainga.ga.OutputMappers
import vsoc.ga.trainga.nn.NeuralNets

object InputDataModelMain extends App {

  val num = 5000

  val handlers = Seq(
    InputDataHandlers.boxPlots("team01om02varL", NeuralNets.team01, OutputMappers.om02varL, Some(100)),
    InputDataHandlers.boxPlots("team02om02varL", NeuralNets.team02, OutputMappers.om02varL, Some(100)),
  )

  for (handler <- handlers) {
    new InputDataModel().run(handler, num)
  }

}



