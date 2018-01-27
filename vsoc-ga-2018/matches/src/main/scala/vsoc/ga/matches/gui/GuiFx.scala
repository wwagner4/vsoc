package vsoc.ga.matches.gui

import javafx.application.Platform
import javafx.embed.swing.SwingNode
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.stage.Stage

import vsoc.ga.matches.Match
import vsoc.ga.matches.impl.MatchResults
import vsoc.server.gui.{FieldPanel, Paintable, SimulationChangeListener}

import scala.concurrent.Future

class GuiFx(val stage: Stage, val _match: Match) extends SimulationChangeListener {

  import scala.concurrent.ExecutionContext.Implicits.global

  var running = false
  var delay = 10

  val txtBgColor = "#a0aaaa" // Equal to field background

  val txt = new Label()

  stage.setTitle("SingleMatch")

  stage.getIcons.add(new Image("/logo.png"))

  val root = new BorderPane()
  val scene = new Scene(root, 900, 600)
  stage.setScene(scene)

  scene.setOnKeyPressed(keyPressed)

  val fieldPanel = new FieldPanel()
  _match.addSimListener(fieldPanel)
  _match.addSimListener(this)
  val swingNode = new SwingNode()
  swingNode.setContent(fieldPanel)

  val vbox = new VBox()
  vbox.setStyle(s"-fx-background-color: $txtBgColor;")

  txt.setStyle(s"-fx-background-color: $txtBgColor;")
  val bw = new BorderWidths(5, 5, 5, 5, false, false, false, false)
  txt.setBorder(new Border(new BorderStroke(Color.valueOf(txtBgColor),
    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, bw)))
  updateInfoText()

  vbox.getChildren.add(txt)

  root.setCenter(swingNode)
  root.setLeft(vbox)

  def startStop(): Unit = {
    if (running) {
      running = false
    }
    else if (!running) {
      running = true
      Future {
        while (running) {
          _match.takeStep()
          Thread.sleep(delay)
        }
      }
    }
    // else nothing to do
  }

  def keyPressed(e: KeyEvent): Unit = {
    e.getCode match {
      case KeyCode.SPACE =>
        startStop()
        updateInfoText()
      case KeyCode.N if delay < 20 =>
        delay += 1
        updateInfoText()
      case KeyCode.N if delay < 50 =>
        delay += 5
        updateInfoText()
      case KeyCode.N if delay < 100 =>
        delay += 10
        updateInfoText()
      case KeyCode.N if delay < 500 =>
        delay += 20
        updateInfoText()
      case KeyCode.M if delay >= 500 =>
        delay -= 20
        updateInfoText()
      case KeyCode.M if delay >= 100 =>
        delay -= 10
        updateInfoText()
      case KeyCode.M if delay >= 50 =>
        delay -= 5
        updateInfoText()
      case KeyCode.M if delay >= 2 =>
        delay -= 1
        updateInfoText()
      case _ => // Ignore
    }

  }

  def updateInfoText(): Unit = {

    val stateStr = MatchResults.formatDefault(_match.state)

    txt.setText(
      s"""INFO
         |Team '${_match.teamWestName}'
         | yellow west (w) ->
         |
         |Team '${_match.teamEastName}'
         | red east (e) <-
         |
         |COMMANDS
         | space - start/stop
         | m - speed up
         | n - slow down
         |
         |STATUS
         |running : $running
         |delay : ${delay}ms
         |
         |$stateStr
      """.stripMargin)
  }

  override def simulationChangePerformed(s: Paintable): Unit = {
    Platform.runLater(() => updateInfoText())
  }

}
