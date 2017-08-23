package vsoc.training

import vsoc.common.{Dat, Formatter}

import scala.util.Random

object RegularisationTraining extends App {

  val bias = Seq(2.0, 1.0, 1.0E-3, 1.0E-5, 0.0)
  val nrOfHiddenNodes = Seq(50, 100, 200)

  val _seed = Random.nextLong()

  Training().run(
    MetaParamRun(
      description = Some("Bias Regularisation"),
      clazz = RegularisationTraining.getClass.toString,
      imgWidth = 1500,
      imgHeight = 1200,
      columns = 3,
      series = for (_bias <- bias) yield {
        MetaParamSeries(
          description = "regularisation bias: " + Formatter.formatNumber("%.1E", _bias),
          descriptionX = "hidden layers",
          yRange = (-20, 20),
          metaParams = for (nr <- nrOfHiddenNodes) yield {
            MetaParam(
              description = s"hiddenLayers:$nr - biasRegularisation:${_bias}",
              seed = _seed,
              regularisation = Some(Regularisation(0.0, 0.0, _bias, _bias)),
              numHiddenNodes = nr,
              variableParmDescription = () => "" + Formatter.formatNumber("%d", nr)
            )
          }
        )
      }
    )
  )

}
