package vsoc.training.vizparam

import java.io.{File, PrintWriter}

import vsoc.common.{Dat, Formatter, UtilIO}
import vsoc.training.{MetaParam, MetaParamRun, MetaParamSeries}

import scala.util.Random

object VizparamTryout extends App {

  val iterations = 100
  val sizeTrainingDatas = List(Dat.Size_50000, Dat.Size_100000, Dat.Size_500000, Dat.Size_1000000)
  val batchSizes = Seq(0.1, 0.5, 0.7, 0.9)

  val series = for (size <- sizeTrainingDatas) yield {
    val params = for (batchSize <- batchSizes) yield {
      MetaParam(
        seed = Random.nextLong(),
        iterations = iterations,
        batchSizeTrainingDataRelative = batchSize,
        variableParmDescription = () => Formatter.formatNumber("%.2f", batchSize),
        trainingData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, size),
        testData = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_5000)
      )
    }
    MetaParamSeries(
      description = "trainingdata size: " + size.size,
      descriptionX = "batch size relative",
      metaParams = params)
  }

  val run = MetaParamRun(
    description = Some(s"batch size relative on different sized datasets. iterations:$iterations"),
    imgWidth = 1800,
    imgHeight = 1200,
    columns = 2,
    series = series)

  val html = Vizparam.html(run)

  val dir = UtilIO.dirSub(UtilIO.dirWork, "vizparam")
  val file = new File(dir, "index.html")

  UtilIO.use(new PrintWriter(file)){pw => pw.print(html)}

  println("wrote to " + file)
}
