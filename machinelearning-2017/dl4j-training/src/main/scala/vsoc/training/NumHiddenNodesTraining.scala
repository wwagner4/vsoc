package vsoc.training

import vsoc.common.Dat

import scala.util.Random

object NumHiddenNodesTraining extends App {

  val sizes = Seq(Dat.Size_1000000)
  val _numHiddenNodes = Seq(5, 10, 30, 50, 100, 200)
  val _seed = Random.nextLong()

  Training().run(
    MetaParamRun(
      description = None,
      clazz = this.getClass.toString,
      imgWidth = 1000,
      imgHeight = 1000,
      columns = 2,
      series = for (size <- sizes) yield {
        MetaParamSeries(
          description = "trainingdata size: " + size.size,
          descriptionX = "numHiddenNodes",
          metaParams = for (num <- _numHiddenNodes) yield {
            MetaParam(
              description = s"sizeDat:$size - iter:$num",
              seed = _seed,
              numHiddenNodes = num,
              variableParmDescription = () => "" + num,
              trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, size),
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_5000)
            )
          }
        )
      }
    )
  )

}
