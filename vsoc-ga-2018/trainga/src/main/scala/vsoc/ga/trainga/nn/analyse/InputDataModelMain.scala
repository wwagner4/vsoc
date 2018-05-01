package vsoc.ga.trainga.nn.analyse

import vsoc.ga.trainga.ga.OutputMappers
import vsoc.ga.trainga.nn.NeuralNets

object InputDataModelMain extends App {

  val num = 5000

  val handlers = Seq(
    //InputDataHandlers.boxPlots("team01om01FOrig", NeuralNets.team01, OutputMappers.om01FDefault, 200),
    //InputDataHandlers.boxPlots("team01om01FSmall", NeuralNets.team01, OutputMappers.om01FSmall, 200),
    //InputDataHandlers.boxPlots("team01omRaw", NeuralNets.team01, OutputMappers.omRaw, 250),
    InputDataHandlers.boxPlots("team01om02", NeuralNets.team01, OutputMappers.om02, Some(50)),
    //InputDataHandlers.boxPlots("team02om01FOrig", NeuralNets.team02, OutputMappers.om01FDefault, 200),
    //InputDataHandlers.boxPlots("team02om01FSmall", NeuralNets.team02, OutputMappers.om01FSmall, 200),
    //InputDataHandlers.boxPlots("team02omRaw", NeuralNets.team02, OutputMappers.om, 250),
    InputDataHandlers.boxPlots("team02om02", NeuralNets.team02, OutputMappers.om02, Some(50)),
  )

  for (handler <- handlers) {
    new InputDataModel().run(handler, num)
  }

}



