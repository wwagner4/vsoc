package vsoc.training

import vsoc.common.{Dat, Formatter}

import scala.util.Random

object RegularisationTraining extends App {

  val bias = Seq(1.0E-2, 1.0E-3, 1.0E-4, 1.0E-5, 1.0E-6, 0.0)
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
          description = "regularisation l1: " + Formatter.formatNumber("%.1E", _bias),
          descriptionX = "hidden layers",
          yRange = (-20, 20),
          metaParams = for (hl <- nrOfHiddenNodes) yield {
            MetaParam(
              description = s"hiddenLayers:$hl - biasRegularisation:${_bias}",
              seed = _seed,
              regularisation = Some(Regularisation(0.0, 0.0, _bias, _bias)),
              variableParmDescription = () => "" + Formatter.formatNumber("%d", hl)
            )
          }
        )
      }
    )
  )

}
