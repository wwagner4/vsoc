package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Dat, Formatter}

import scala.util.Random

object LearningRateIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])

  val _iterations = 50

  val learningRates = Seq((0.01, "10^-2"), (0.001, "10^-3"), (0.0001, "10^-4"), (0.00001, "10^-5"))
  val sizeTrainingDatas = List(Dat.Size_10000, Dat.Size_50000, Dat.Size_100000, Dat.Size_500000)

  val  series = for ((lr, lrDesc) <- learningRates) yield {
    val mpar = for (sizeDat <- sizeTrainingDatas) yield {
      MetaParam(
        seed = Random.nextLong(),
        learningRate = lr,
        trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, sizeDat),
        testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, sizeDat),
        iterations = _iterations,
        variableParmDescription = () => sizeDat.size.toString
      )
    }
    MetaParamSeries(
      description = "learning rate: " + lrDesc,
      descriptionX = "size",
      metaParams = mpar
    )
  }

  val run = MetaParamRun(
    description = Some("test learning rate | iterations: " + _iterations),
    imgWidth = 1000,
    imgHeight = 1500,
    columns = 2,
    series = series)

  new Training(log).trainSeries(run)

}
