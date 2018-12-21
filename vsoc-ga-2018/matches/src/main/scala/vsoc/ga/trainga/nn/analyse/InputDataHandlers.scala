package vsoc.ga.trainga.nn.analyse

import java.nio.file.{Files, Path}

import atan.model.{Player, ViewAngle, ViewQuality}
import entelijan.viz.{Viz, VizCreator, VizCreators}
import vsoc.ga.matches.behav.OutputMapperNn
import vsoc.ga.trainga.nn.NeuralNet

object InputDataHandlers {

  def stdOut: InputDataHandler = new InputDataHandler {

    override def handle(in: Array[Double]): Unit = {
      println(in.toList.mkString(", "))
    }

    override def close(): Unit = () // Nothing to do
  }


  def boxPlots(_id: String, _nn: NeuralNet, _outputMapperNn: OutputMapperNn, _minMax: Option[Int], wd: Path): InputDataHandler =
    new InputDataHandler {

      val player: CollectingPlayer = new CollectingPlayer

      override def handle(in: Array[Double]): Unit = {
        val out = _nn.output(in)
        _outputMapperNn.applyOutput(player, out)
      }

      override def close(): Unit = {

        implicit val vizCreator: VizCreator[Viz.X] = {
          val scriptDir = wd.resolve(".script")
          Files.createDirectories(scriptDir)
          val imgDir = wd.resolve("viz_img")
          Files.createDirectories(imgDir)
          VizCreators.gnuplot(scriptDir.toFile, imgDir.toFile, execute=true, classOf[Viz.X])
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
          id = s"${_id}",
          title = s"${_id}",
          yRange = _minMax.map(mm => Viz.Range(Some(-mm), Some(mm))),
          dataRows = dataRows
        )

        Viz.createDiagram(dia)

      }

    }
}

abstract class InputHandlerNNBoxPlots extends InputDataHandler {

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

