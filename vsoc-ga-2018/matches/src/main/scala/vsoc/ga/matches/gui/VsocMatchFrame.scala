package vsoc.ga.matches.gui

import java.awt._
import java.awt.event.{KeyEvent, KeyListener}

import javax.swing._
import javax.swing.border.EmptyBorder
import vsoc.ga.matches.{Match, MatchResults}
import vsoc.server.gui.{FieldPanel, Paintable, SimulationChangeListener}

import scala.concurrent.Future

class VsocMatchFrame(title: String, subtitle: String, matchFactory: () => Match) extends JFrame with SimulationChangeListener with KeyListener {

  import concurrent.ExecutionContext.Implicits.global

  var _match = Option.empty[Match]
  val fieldPanel = new FieldPanel()

  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setSize(1200, 600)
  setLocationByPlatform(true)
  setTitle(title)

  private var running = false
  private var delay = SpeedManager.DEFAULT_DELAY

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
          _match.get.takeStep()
          pause(delay)
        }
      }
    }
    // else nothing to do
  }


  def pause(millis: Int):Unit = Thread.sleep(millis)

  def restartMatch(): Unit = {
    running = false
    pause(delay + 200)
    createNewMatch()
  }

  override def keyReleased(e: KeyEvent): Unit = {
    e.getExtendedKeyCode match {
      case KeyCode.SPACE =>
        startStop()
      case KeyCode.N =>
        SpeedManager.slowDown()
      case KeyCode.M =>
        SpeedManager.speedUp()
      case KeyCode.B =>
        SpeedManager.resetSpeed()
      case KeyCode.R =>
        restartMatch()
      case _ => // Ignore
    }
    updateInfoText()
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
    fieldPanel.setFocusable(false)
    createNewMatch()
    fieldPanel
  }

  def createNewMatch(): Unit = {
    _match = Some(matchFactory())
    _match.get.addSimListener(fieldPanel)
    _match.get.addSimListener(this)

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

    val stateStr = MatchResults.formatDefault(_match.get.state, colorEast = "red", colorWest = "yellow")

    txt.setText(
      s"""$title
         |$subtitle
         |
         |Team '${_match.get.teamWestName}' yellow
         |Team '${_match.get.teamEastName}' red
         |
         |COMMANDS
         | space - start/stop
         | m - speed up
         | n - slow down
         | b - reset speed
         | r - restart match
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
    val R = 82
    val B = 66

  }

  object SpeedManager {

    val DEFAULT_DELAY = 10

    def resetSpeed(): Unit = {
      delay = DEFAULT_DELAY
    }


    def speedUp(): Unit = {
      if (delay >= 500) delay -= 20
      else if (delay >= 100) delay -= 10
      else if (delay >= 50) delay -= 5
      else if (delay >= 1) delay -= 1
      // else nothing to do
    }

    def slowDown(): Unit = {
      if (delay < 20) delay += 1
      else if (delay < 50) delay += 5
      else if (delay < 100) delay += 10
      else if (delay < 500) delay += 20
      // else nothing to do
    }

  }

}

