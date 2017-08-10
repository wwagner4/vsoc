package vsoc.training

import org.slf4j.{Logger, LoggerFactory}

import scala.util.Random

object LearningRateIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val metaParams: Seq[MetaParam] = Seq(0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001).map { value =>
    MetaParam(
      description= "LearningRate",
      seed = Random.nextLong(),
      learningRate = value,
      iterations = 50)
  }

  val sizeTrainingDatas = List(50000, 200000, 500000, 1000000)

  val series = sizeTrainingDatas.map{s =>
    val title = "Trainingdata size:" + s
    val mpar = metaParams.map(mp => mp.copy(sizeTrainingData = s, batchSizeTrainingData = s / 10))
    MetaParamSeries(title, mpar)}

  val run = MetaParamRun(imgWidth = 1000, imgHeight = 1200, columns = 2, series = series)
  new Training(log).trainSeries(run)


}
