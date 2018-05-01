package vsoc.ga.trainga.nn.analyse

import java.nio.file.Files

import atan.model.{Player, ViewAngle, ViewQuality}
import entelijan.viz.{Viz, VizCreator, VizCreatorGnuplot}
import vsoc.ga.common.config.ConfigHelper
import vsoc.ga.trainga.behav.OutputMapperNn
import vsoc.ga.trainga.ga.OutputFactors
import vsoc.ga.trainga.nn.NeuralNet

object InputDataHandlers {

  def stdOut: InputDataHandler = new InputDataHandler {

    override def handle(in: Array[Double]): Unit = {
      println(in.toList.mkString(", "))
    }

    override def close(): Unit = () // Nothing to do
  }


  def boxPlots(_id: String, _nn: NeuralNet, _outputMapperNn: OutputMapperNn): InputDataHandler =
    new InputHandlerNNBoxPlots {

      override def createNn: NeuralNet = _nn

      override def id: String = _id

      override def outMapper: OutputMapperNn = _outputMapperNn

    }
}

abstract class InputHandlerNNBoxPlots extends InputDataHandler {

  def createNn: NeuralNet

  def id: String

  private val nn = createNn

  val of = OutputFactors(50, 20, 5)

  def outMapper: OutputMapperNn

  val player: CollectingPlayer = new CollectingPlayer

  override def handle(in: Array[Double]): Unit = {
    val out = nn.output(in)
    outMapper.applyOutput(player, out)
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
      id = s"$id",
      title = s"$id",
      yRange = Some(Viz.Range(Some(-200), Some(200))),
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

