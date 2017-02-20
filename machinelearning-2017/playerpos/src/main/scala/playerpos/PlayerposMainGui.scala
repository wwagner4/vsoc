package playerpos

import scala.swing._
import java.awt.Color
import scala.swing.event.ButtonClicked
import atan.model.Player
import java.util.Optional
import vsoc.behaviour.BehaviourController
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import vsoc.server.ServerUtil
import vsoc.server.gui.FieldCanvas
import javax.swing.JComponent
import javax.swing.JPanel
import java.util.concurrent.Executors
import scala.concurrent.Future
import java.awt.BorderLayout

object PlayerposMainGui extends SimpleSwingApplication {

  import scala.concurrent.ExecutionContext.Implicits._

  val behav = new Behaviour() {

    private val ran = new java.util.Random()

    def shouldBeApplied(sensors: Sensors): Boolean = true

    def apply(sensors: Sensors, player: Player): Unit = {
      player.move(ran.nextInt(100) - 50, ran.nextInt(100) - 50)
      player.turn(ran.nextInt(360))
    }

    def getChild(): Optional[Behaviour] = Optional.empty()

  }

  val ctrl = new BehaviourController(behav)

  val srv = ServerUtil.current().createServer(1, 0)
  srv.getPlayersEast.get(0).setController(ctrl)

  def top = new MainFrame {

    import scala.swing.BorderPanel._

    val appPanel = new BorderPanel {

      val serverPanel = new Component {
        val fc = new FieldCanvas
        fc.setSim(srv)
        val jc = new JPanel(new BorderLayout)
        jc.add(fc, BorderLayout.CENTER)
        initP = jc
      }

      var cnt = 0

      val buttons = new FlowPanel {
        val button1 = new Button {
          text = "TakeStep"
          reactions += {
            case ButtonClicked(_) =>
              Future {
                println("take step " + cnt)
                srv.takeStep()
                cnt += 1
              }
          }
        }
        val button2 = new Button {
          text = "Take 10 Steps"
          reactions += {
            case ButtonClicked(_) =>
              Future {
                (1 to 10) foreach { _ =>
                  println("take step " + cnt)
                  srv.takeStep()
                  cnt += 1
                }
                serverPanel.repaint()
              }
          }
        }
        contents += button1
        contents += button2
      }

      layout(serverPanel) = Position.Center
      layout(buttons) = Position.South

    }
    title = "Vsoc Playerpos"
    minimumSize = new Dimension(800, 600)
    centerOnScreen()

    contents = appPanel
  }
}