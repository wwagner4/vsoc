package vsoc.ga.trainga

import java.nio.file.Files

import entelijan.viz.{Viz, VizCreator, VizCreators}
import vsoc.ga.matches.Activation
import vsoc.ga.trainga.config.ConfigHelper

object ActivationTryout extends App {

  implicit val vizCreator: VizCreator[Viz.XY] = {
    val wd = ConfigHelper.workDir
    val scriptDir = wd.resolve(".script")
    Files.createDirectories(scriptDir)
    val imgDir = wd.resolve("viz_img")
    Files.createDirectories(imgDir)
    VizCreators.gnuplot(scriptDir = scriptDir.toFile, imageDir = imgDir.toFile, clazz = classOf[Viz.XY])
  }

  val ks = Seq(0.1, 0.2, 0.5, 0.8, 1.0)
  val yr = Some(Viz.Range(Some(-1.5), Some(1.5)))
  val xs = -60 to 60 by 1

  val sigDrs = ks.map { k =>
    val data = xs.map(x => Viz.XY(x, Activation.sigmoid(k)(x)))
    Viz.DataRow(name = Some("sigmoid %.2f".format(k)), data = data)
  }

  val sigDia = Viz.Diagram(id = "sig", title = "Sigmoid", dataRows = sigDrs, yRange = yr)
  Viz.createDiagram(sigDia)

  val tanhDrs = ks.map { k =>
    val data = xs.map(x => Viz.XY(x, Activation.tanh(k)(x)))
    Viz.DataRow(name = Some("tanh %.2f".format(k)), data = data)
  }

  val tanhDia = Viz.Diagram(id = "tanh", title = "Tanh", dataRows = tanhDrs, yRange = yr)

  Viz.createDiagram(tanhDia)
}

