package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Dat, Formatter}

import scala.util.Random

object LearningRateIterationsTraining extends App {

  val _iterations = 50

  val learningRates = Map(0.01 -> "10^-2", 0.001 -> "10^-3", 0.0001 -> "10^-4", 0.00001 -> "10^-5")

  val log: Logger = LoggerFactory.getLogger(classOf[Training])

  val metaParams: Seq[MetaParam] = learningRates.keys.toSeq.sorted.reverse.map { value =>
    MetaParam(
      seed = Random.nextLong(),
      learningRate = value,
      iterations = _iterations,
      variableParmDescription = () => learningRates(value)
    )
  }

  val sizeTrainingDatas = List(Dat.Size_10000, Dat.Size_50000, Dat.Size_100000, Dat.Size_500000)

  val series = sizeTrainingDatas.map{sizeDat =>
    val mpar = metaParams.map{mp =>
      val desc = mp.trainingData.copy(size = sizeDat)
      mp.copy(trainingData = desc)}
    MetaParamSeries(
      description = "trainingdata size: " + Formatter.formatNumber("%d", sizeDat.size),
      descriptionX = "learning rate",
      metaParams = mpar
    )

  }

  val run = MetaParamRun(
    description = Some("Test Learning Rate. Iterations: " + _iterations),
    imgWidth = 1000,
    imgHeight = 1200,
    columns = 2,
    series = series)

  new Training(log).trainSeries(run)

}
