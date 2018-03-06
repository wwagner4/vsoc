package vsoc.ga.matches.gui

import java.awt._
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing._
import javax.swing.border.EmptyBorder

import vsoc.ga.matches.{Match, MatchResults}
import vsoc.server.gui.{FieldPanel, Paintable, SimulationChangeListener}

import scala.concurrent.Future

class VsocMatchFrame(_match: Match) extends JFrame with SimulationChangeListener with KeyListener {

  import concurrent.ExecutionContext.Implicits.global

  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setSize(1200, 600)
  setLocationByPlatform(true)

  var running = false
  var delay = 10

  val txt = new JTextArea()
  txt.setEditable(false)
  txt.setFocusable(false)

  rootPane.setContentPane(vsocPane)

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


  override def keyReleased(e: KeyEvent): Unit = {
    e.getExtendedKeyCode match {
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

  override def keyPressed(e: KeyEvent): Unit = ()

  override def keyTyped(e: KeyEvent): Unit = ()

  addKeyListener(this)

  def vsocPane: Container = {
    val p = new JPanel()
    p.setBackground(Color.YELLOW)
    p.setLayout(new BorderLayout())
    p.add(vsocLeftPane, BorderLayout.WEST)
    p.add(vsocCenterPane, BorderLayout.CENTER)
    updateInfoText()
    p
  }

  def vsocCenterPane: Component = {
    val fieldPanel = new FieldPanel()
    fieldPanel.setFocusable(false)
    _match.addSimListener(fieldPanel)
    _match.addSimListener(this)
    fieldPanel
  }


  def vsocLeftPane: Component = {
    val p = new JPanel()
    p.setPreferredSize(new Dimension(200, Integer.MAX_VALUE))
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS))
    txt.setBorder(new EmptyBorder(5, 5, 5, 5))
    p.add(txt)
    p
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
    SwingUtilities.invokeLater(() => updateInfoText())
  }

  case object KeyCode {

    val SPACE = 32
    val N = 78
    val M = 77

  }

}

