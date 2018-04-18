package vsoc.ga.trainga.nn.analyse

import java.nio.file.Files

import atan.model.{Player, ViewAngle, ViewQuality}
import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import vsoc.ga.common.config.ConfigHelper
import vsoc.ga.trainga.behav.OutputMapperNn
import vsoc.ga.trainga.ga.impl.OutputMapperNnTeam01
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

  private var cnt = 0

  val outMapper: OutputMapperNn = new OutputMapperNnTeam01
  val player: CollectingPlayer = new CollectingPlayer

  override def handle(in: Array[Double]): Unit = {
    val out = nn.output(in)
    outMapper.applyOutput(player, out)
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
        data = player.dashPower
      ),
      Viz.DataRow(
        name = Some("kick power"),
        style = Viz.Style_BOXPLOT,
        data = player.kickPower
      ),
      Viz.DataRow(
        name = Some("kick dir"),
        style = Viz.Style_BOXPLOT,
        data = player.kickDirection
      ),
      Viz.DataRow(
        name = Some("turn"),
        style = Viz.Style_BOXPLOT,
        data = player.turn
      )
    )
    val dia = Viz.Diagram[Viz.X](
      id = s"nnBoxPlots$id$cnt",
      title = s"Output Data NN id:$id n:$cnt",
      yRange = Some(Viz.Range(Some(-100), Some(100))),
      dataRows = dataRows
    )

    Viz.createDiagram(dia)

  }
}

class CollectingPlayer extends Player {

  var dashPower = List.empty[Viz.X]
  var kickPower = List.empty[Viz.X]
  var kickDirection = List.empty[Viz.X]
  var turn = List.empty[Viz.X]

  override def dash(power: Int): Unit = dashPower ::= Viz.X(power)

  override def move(x: Int, y: Int): Unit = throw new IllegalStateException("should never be called")

  override def kick(power: Int, direction: Double): Unit = {
    kickPower ::= Viz.X(power)
    kickDirection ::= Viz.X(direction)
  }

  override def say(message: String): Unit = throw new IllegalStateException("should never be called")

  override def senseBody(): Unit = throw new IllegalStateException("should never be called")

  override def turn(angle: Double): Unit = turn ::= Viz.X(angle)

  override def turnNeck(angle: Double): Unit = throw new IllegalStateException("should never be called")

  override def catchBall(direction: Double): Unit = throw new IllegalStateException("should never be called")

  override def changeViewMode(quality: ViewQuality, angle: ViewAngle): Unit = throw new IllegalStateException("should never be called")

  override def bye(): Unit = throw new IllegalStateException("should never be called")

  override def handleError(error: String): Unit = throw new IllegalStateException("should never be called")

  override def getTeamName: String = throw new IllegalStateException("should never be called")

  override def isTeamEast: Boolean = throw new IllegalStateException("should never be called")

  override def setTeamEast(is: Boolean): Unit = throw new IllegalStateException("should never be called")

  override def setNumber(num: Int): Unit = throw new IllegalStateException("should never be called")

  override def getNumber: Int = throw new IllegalStateException("should never be called")
}

