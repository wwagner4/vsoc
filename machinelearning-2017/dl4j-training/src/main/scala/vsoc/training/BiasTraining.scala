package vsoc.training

import vsoc.common.Dat

import scala.util.Random

object BiasTraining extends App {

  val ids = Seq(Dat.Id_A, Dat.Id_B, Dat.Id_C, Dat.Id_D, Dat.Id_E)
  val numHiddenNodes = Seq(100, 300, 500)
  val _seed = Random.nextLong()

  Training().run(
    MetaParamRun(
      description = Some("Bias"),
      descriptionMd = Some(
        """
          |Testset to show that the learned bias is independend from the
          |testdata.
        """.stripMargin),
      clazz = BatchSizeTraining.getClass.toString,
      imgWidth = 2000,
      imgHeight = 1000,
      columns = 3,
      series = for (_num <- numHiddenNodes) yield
        MetaParamSeries(
          description = "hidden nodes " + _num,
          descriptionX = "testdataset",
          yRange = (10, 10),
          metaParams = for (id <- ids) yield {
            MetaParam(
              id = Some(id + "_" + _num),
              numHiddenNodes = _num,
              description = s"testset id:$id",
              seed = _seed,
              testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, id, Dat.Size_1000),
              variableParmDescription = () => id.code
            )
          }
        )
    )
  )
}
