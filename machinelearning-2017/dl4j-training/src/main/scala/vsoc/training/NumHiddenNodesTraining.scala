package vsoc.training

import vsoc.common.Dat

import scala.util.Random

object NumHiddenNodesTraining extends App {

  val _numHiddenNodes = Seq(50, 100, 200, 500, 700)
  val seeds = Seq(
    Random.nextLong(),
    Random.nextLong(),
    Random.nextLong(),
    Random.nextLong()
  )

  Training().run(
    MetaParamRun(
      description = Some("Number of hidden nodes"),
      clazz = this.getClass.toString,
      imgWidth = 1000,
      imgHeight = 1000,
      columns = 2,
      series = for (_seed <- seeds) yield {
        MetaParamSeries(
          description = "Seed " + _seed,
          descriptionX = "numHiddenNodes",
          yRange = (-20, 20),
          metaParams = for (num <- _numHiddenNodes) yield {
            MetaParam(
              description = s"seed:${_seed} - numOfHiddenNodes:$num",
              seed = _seed,
              numHiddenNodes = num,
              variableParmDescription = () => "" + num,
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_1000)
            )
          }
        )
      }
    )
  )

}
