package vsoc.training

import org.deeplearning4j.nn.api.OptimizationAlgorithm
import vsoc.common.Dat

import scala.util.Random

object OptAlgoTraining extends App {

  val _iterations = 300
  val _seed = Random.nextLong()

  val optAlgos = Seq(
    OptimizationAlgorithm.CONJUGATE_GRADIENT,
    OptimizationAlgorithm.LBFGS,
    OptimizationAlgorithm.LINE_GRADIENT_DESCENT,
    OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT
  )
  val sizes = Seq(
    Dat.Size_50000,
    Dat.Size_100000,
    Dat.Size_500000,
    Dat.Size_1000000
  )

  Training().run(
    MetaParamRun(
      description = Some("Test Optimzation Algorithm | iterations: " + _iterations),
      clazz = LearningRateIterationsTraining.getClass.toString,
      imgWidth = 1500,
      imgHeight = 1200,
      columns = 2,
      series = for (size <- sizes) yield {
        MetaParamSeries(
          description = "size: " + size.size.toString,
          descriptionX = "Optimzation Algorithm",
          metaParams = for (_optAlgo <- optAlgos) yield {
            MetaParam(
              description = s"""size:$size - optAlgo:${_optAlgo}""",
              seed = _seed,
              trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, size),
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000),
              iterations = _iterations,
              optAlgo = _optAlgo,
              variableParmDescription = () => formatOptAlgo(_optAlgo)
            )
          }
        )
      }
    )
  )

  def formatOptAlgo(optAlgo: OptimizationAlgorithm): String = {
    optAlgo match {
      case OptimizationAlgorithm.CONJUGATE_GRADIENT => "CG"
      case OptimizationAlgorithm.LBFGS => "LB"
      case OptimizationAlgorithm.LINE_GRADIENT_DESCENT => "LG"
      case OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT => "SG"
      case _ => "??"
    }
  }

}
