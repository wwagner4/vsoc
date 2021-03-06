package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.{Dat, Formatter}

import scala.util.Random

object BatchSizeTraining extends App {

  val iterationsList = List(10, 50, 100, 200)
  val sizeTrainingDatas = Dat.Size_1000000
  val batchSizes = Seq(0.1, 0.2, 0.5, 0.8)
  val _seed = Random.nextLong()

  val series = for (iterations <- iterationsList) yield {
    val params = for (batchSize <- batchSizes) yield {
      MetaParam(
        description = s"""iter:$iterations - batchsize:${Formatter.formatNumber("%.2f", batchSize)}""",
        seed = _seed,
        iterations = iterations,
        batchSizeTrainingDataRelative = batchSize,
        variableParmDescription = () => Formatter.formatNumber("%.2f", batchSize),
        trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, sizeTrainingDatas),
        testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000)
      )
    }
    MetaParamSeries(
      description = "iterations: " + iterations,
      descriptionX = "batch size relative",
      metaParams = params)
  }

  val run = MetaParamRun(
    description = Some(s"batch size relative with different iterations. dataset size:${sizeTrainingDatas.size}"),
    clazz = BatchSizeTraining.getClass.toString,
    imgWidth = 1800,
    imgHeight = 1200,
    columns = 2,
    series = series)

  Training().run(run)

}
