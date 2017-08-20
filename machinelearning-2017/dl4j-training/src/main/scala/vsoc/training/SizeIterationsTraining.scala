package vsoc.training

import vsoc.common.Dat

import scala.util.Random

object SizeIterationsTraining extends App {

  val sizes = List(Dat.Size_100000, Dat.Size_500000, Dat.Size_1000000, Dat.Size_5000000)
  val iterations = Seq(50, 100, 200, 500)
  val _seed = Random.nextLong()

  Training().run(
    MetaParamRun(
      description = Some("multiple iterations for different sized datasets"),
      clazz = SizeIterationsTraining.getClass.toString,
      imgWidth = 1500,
      imgHeight = 1000,
      columns = 2,
      series = for (size <- sizes) yield {
        MetaParamSeries(
          description = "trainingdata size: " + size.size,
          descriptionX = "iterations",
          metaParams = for (iter <- iterations) yield {
            MetaParam(
              description = s"sizeDat:$size - iter:$iter",
              seed = _seed,
              iterations = iter,
              variableParmDescription = () => "" + iter,
              trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, size),
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_5000)
            )
          }
        )
      }
    )
  )

}
