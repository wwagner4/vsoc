package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Dat, Formatter}

import scala.util.Random

object LearningRateIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])

  val _iterations = 500
  val _seed = Random.nextLong()

  val learningRates = Seq(0.0005, 0.0001, 0.00005, 0.00001)
  val sizeTrainingDatas = List(Dat.Size_50000, Dat.Size_100000, Dat.Size_500000, Dat.Size_1000000)

  val  series = for (lr <- learningRates) yield {
    val mpar = for (sizeDat <- sizeTrainingDatas) yield {
      MetaParam(
        seed = _seed,
        learningRate = lr,
        batchSizeTrainingDataRelative = 0.5,
        trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, sizeDat),
        testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000),
        iterations = _iterations,
        variableParmDescription = () => sizeDat.size.toString
      )
    }
    MetaParamSeries(
      description = "learning rate: " + Formatter.formatNumber("%.2E", lr),
      descriptionX = "size",
      metaParams = mpar
    )
  }

  val run = MetaParamRun(
    description = Some("test learning rate | iterations: " + _iterations),
    clazz = LearningRateIterationsTraining.getClass.toString,
    imgWidth = 1000,
    imgHeight = 1500,
    columns = 2,
    series = series)

  new Training(log).trainSeries(run)

}
