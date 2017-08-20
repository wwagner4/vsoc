package vsoc.training

import vsoc.common.{Dat, Formatter}

import scala.util.Random

object RegularisationTraining extends App {

  val l1s = Seq(1.0E-2, 1.0E-4)
  val l2s = Seq(1.0E-2, 1.0E-4)
//  val l1s = Seq(0.0, 1.0E-2, 1.0E-3, 1.0E-4)
//  val l2s = Seq(0.0, 1.0E-2, 1.0E-3, 1.0E-4)

  val _seed = Random.nextLong()

  Training().run(
    MetaParamRun(
      description = Some("Regularisation l1 l2"),
      clazz = SizeIterationsTraining.getClass.toString,
      imgWidth = 1500,
      imgHeight = 1000,
      columns = 2,
      series = for (l1 <- l1s) yield {
        MetaParamSeries(
          description = "regularisation l1: " + Formatter.formatNumber("%.2E", l1),
          descriptionX = "regularisation l2",
          metaParams = for (l2 <- l2s) yield {
            MetaParam(
              description = s"l1:$l1 - l2:$l2",
              seed = _seed,
              variableParmDescription = () => "" + Formatter.formatNumber("%.2E", l2),
              trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, Dat.Size_500000),
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_5000)
            )
          }
        )
      }
    )
  )

}
