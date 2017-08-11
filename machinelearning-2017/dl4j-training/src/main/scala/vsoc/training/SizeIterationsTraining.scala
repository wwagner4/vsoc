package vsoc.training

import org.slf4j.{Logger, LoggerFactory}

import scala.util.Random

object SizeIterationsTraining extends App {

  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val metaParams: Seq[MetaParam] = Seq(1, 10, 50, 100, 500).map { iter =>
    MetaParam(
      description= "Iterations",
      seed = Random.nextLong(),
      iterations = iter,
      variableParmDescription = () => "" + iter
    )
  }

  val sizeTrainingDatas = List(100000, 200000, 500000, 1000000)

  val series = sizeTrainingDatas.map{size =>
    val title = "Trainingdata size:" + size
    val mpar = metaParams.map(mp => mp.copy(
      sizeTrainingData = size))
    MetaParamSeries(title, mpar)}

  val run = MetaParamRun(imgWidth = 1800, imgHeight = 1200, columns = 2, series = series)
  new Training(log).trainSeries(run)


}
