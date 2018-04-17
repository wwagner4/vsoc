package vsoc.ga.trainga.nn.analyse

import java.nio.file.{Files, Path}

import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import vsoc.ga.common.config.ConfigHelper
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

object InputDataHandlers {

  def stdOut: InputDataHandler = new InputDataHandler {

    override def handle(in: Array[Double]): Unit = {
      println(in.toList.mkString(", "))
    }

    override def close(): Unit = () // Nothing to do
  }


  def nnTeam01: InputDataHandler = new InputDataHandler {
    private val nn = NeuralNets.team01

    override def handle(in: Array[Double]): Unit = {
      println(nn.output(in).toList.mkString(", "))
    }

    override def close(): Unit = () // Nothing to do
  }

  def nnTeam01BoxPlots(_id: String): InputDataHandler = new InputHandlerNNBoxPlots {

    override def createNn: NeuralNet = NeuralNets.team01

    override def id: String = s"Team01${_id}"

  }

  def nnTeam02BoxPlots(_id: String): InputDataHandler = new InputHandlerNNBoxPlots {

    override def createNn: NeuralNet = NeuralNets.team02

    override def id: String = s"Team02${_id}"

  }
}

abstract class InputHandlerNNBoxPlots extends InputDataHandler {

  def createNn: NeuralNet

  def id: String

  private val nn = createNn

  private var dashPower = List.empty[Viz.X]
  private var kickPower = List.empty[Viz.X]
  private var kickDirection = List.empty[Viz.X]
  private var turn = List.empty[Viz.X]
  private var cnt = 0

  override def handle(in: Array[Double]): Unit = {
    val out = nn.output(in)
    dashPower ::= Viz.X(out(0))
    kickPower ::= Viz.X(out(1))
    kickDirection ::= Viz.X(out(2))
    turn ::= Viz.X(out(3))
    cnt += 1
  }

  override def close(): Unit = {

    implicit val createCreator: VizCreator[Viz.X] = {
      val wd = ConfigHelper.workDir
      val scriptDir = wd.resolve(".script")
      Files.createDirectories(scriptDir)
      val imgDir = wd.resolve("viz_img")
      Files.createDirectories(imgDir)
      VizCreatorGnuplot[Viz.X](scriptDir = scriptDir.toFile, imageDir = imgDir.toFile)
    }

    val dataRows = Seq(
      Viz.DataRow(
        name = Some("dash power"),
        style = Viz.Style_BOXPLOT,
        data = dashPower
      ),
      Viz.DataRow(
        name = Some("kick power"),
        style = Viz.Style_BOXPLOT,
        data = kickPower
      ),
      Viz.DataRow(
        name = Some("kick dir"),
        style = Viz.Style_BOXPLOT,
        data = kickDirection
      ),
      Viz.DataRow(
        name = Some("turn"),
        style = Viz.Style_BOXPLOT,
        data = turn
      )
    )
    val dia = Viz.Diagram[Viz.X](
      id = s"nnBoxPlots$id$cnt",
      title = s"Output Data NN id:$id n:$cnt",
      yRange = Some(Viz.Range(Some(-1.5), Some(1.5))),
      dataRows = dataRows
    )

    Viz.createDiagram(dia)

  }
}

