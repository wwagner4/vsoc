package vsoc.training

import org.slf4j.{Logger, LoggerFactory}

import scala.util.Random

object LearningRateIterationsTraining extends App {

  val _iterations = 50

  val learningRates = Map(0.01 -> "10^-2", 0.001 -> "10^-3", 0.0001 -> "10^-4", 0.00001 -> "10^-5", 0.000001 -> "10^-6")

  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val metaParams: Seq[MetaParam] = learningRates.keys.toSeq.sorted.reverse.map { (value: Double) =>
    MetaParam(
      description= "Learning Rate",
      seed = Random.nextLong(),
      learningRate = value,
      iterations = _iterations,
      variableParmDescription = () => learningRates(value)
    )
  }

  val sizeTrainingDatas = List(50000, 200000, 500000, 1000000)

  val series = sizeTrainingDatas.map{s =>
    val title = "Trainingdata size:" + s
    val mpar = metaParams.map(mp => mp.copy(sizeTrainingData = s))
    MetaParamSeries(title, mpar)}

  val run = MetaParamRun(
    description = Some("Test Learning Rate. Iterations: " + _iterations),
    imgWidth = 1000,
    imgHeight = 1200,
    columns = 2,
    series = series)
  new Training(log).trainSeries(run)


}
