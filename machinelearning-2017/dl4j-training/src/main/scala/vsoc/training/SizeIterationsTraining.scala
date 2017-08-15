package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.Dat

import scala.util.Random

object SizeIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])

  val sizeTrainingDatas = List(Dat.Size_50000, Dat.Size_100000, Dat.Size_500000, Dat.Size_1000000)
  val iterations = Seq(1, 50, 100, 200)

  val series = for (size <- sizeTrainingDatas) yield {
    val params = for (iter <- iterations) yield {
      MetaParam(
        seed = Random.nextLong(),
        iterations = iter,
        variableParmDescription = () => "" + iter,
        trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, size),
        testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_5000)
      )
    }
    MetaParamSeries(
      description = "trainingdata size: " + size.size,
      descriptionX = "iterations",
      metaParams = params)
  }

  val run = MetaParamRun(
    description = Some("test the effect of multiple iterations for different sized datasets"),
    imgWidth = 1800,
    imgHeight = 1200,
    columns = 2,
    series = series)

  new Training(log).trainSeries(run)

}
