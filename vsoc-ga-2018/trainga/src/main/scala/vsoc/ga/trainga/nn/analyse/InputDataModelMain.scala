package vsoc.ga.trainga.nn.analyse

import vsoc.ga.trainga.ga.OutputMappers
import vsoc.ga.trainga.nn.NeuralNets

object InputDataModelMain extends App {

  val num = 5000

  val handlers = Seq(
    InputDataHandlers.boxPlots("team01om01FOrig", NeuralNets.team01, OutputMappers.om01FDefault),
    InputDataHandlers.boxPlots("team01om01FSmall", NeuralNets.team01, OutputMappers.om01FSmall),
    InputDataHandlers.boxPlots("team02om01FOrig", NeuralNets.team02, OutputMappers.om01FDefault),
    InputDataHandlers.boxPlots("team02om01FSmall", NeuralNets.team02, OutputMappers.om01FSmall),
  )

  for (handler <- handlers) {
    new InputDataModel().run(handler, num)
  }

}



