package vsoc.training

import scala.util.Random

object ManualBiasTraining extends App {

  val ids = Seq("A", "B", "C", "D", "E", "F", "G", "H")
  val numHiddenNodes = Seq(100, 200, 300, 500)

  Training().run(
    MetaParamRun(
      description = Some("Manual Bias"),
      clazz = BatchSizeTraining.getClass.toString,
      imgWidth = 2000,
      imgHeight = 1200,
      columns = 2,
      series = for (_num <- numHiddenNodes) yield
        MetaParamSeries(
          description = "hidden nodes " + _num,
          descriptionX = "id",
          metaParams = for (id <- ids) yield {
            MetaParam(
              id = Some(id + "_" + _num),
              numHiddenNodes = _num,
              description = s"running id:$id ${_num}",
              seed = Random.nextLong(),
              variableParmDescription = () => id
            )
          }
        )
    )
  )
}
