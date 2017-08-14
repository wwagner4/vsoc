package vsoc.training

import org.slf4j.{Logger, LoggerFactory}
import vsoc.common.Dat

import scala.util.Random

object SizeIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val metaParams: Seq[MetaParam] = Seq(1, 10).map { iter =>
    MetaParam(
      seed = Random.nextLong(),
      iterations = iter,
      variableParmDescription = () => "" + iter
    )
  }

  val sizeTrainingDatas = List(Dat.Size_50000, Dat.Size_100000, Dat.Size_500000, Dat.Size_1000000)

  val series = sizeTrainingDatas.map { size =>
    val mpar = metaParams.map { mp =>
      val desc = mp.trainingData.copy(size = size)
      mp.copy(trainingData = desc)
    }

    MetaParamSeries(
      description = "trainingdata size: " + size.size,
      descriptionX = "iterations",
      metaParams = mpar)
  }

  val run = MetaParamRun(
    description = Some("test the effect of multiple iterations"),
    imgWidth = 1800,
    imgHeight = 1200,
    columns = 2,
    series = series)

  new Training(log).trainSeries(run)


}
