package playerpos

import java.awt.Color
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
import javax.swing.SwingUtilities
import javax.swing.JFrame
import java.awt.event.WindowListener
import java.awt.event.WindowEvent
import javax.swing.event._
import javax.swing._
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.FlowLayout
import vsoc.server.gui.FieldPanel
import vsoc.server.gui.Simulation
import vsoc.server.Server
import scala.collection.JavaConversions._
import atan.model.Controller
import scala.util.Random
import vsoc.server.VsocPlayer

object PlayerposMainGui extends App {

  val f = new FieldFrame()
  f.setSize(900, 600)
  f.setVisible(true)

}

class FieldFrame extends JFrame with WindowListener {
  
  val rand = new Random
  
  def createServer: Server = {
    val s = ServerUtil.current().createServer(1, 0)
    for (p <- s.getPlayers) {
      p.setController(createController)
    }
    s
  }

  def ran(from: Int, to: Int): Int = {
    require(from < to)
    val w = to - from
    val off = w / 2
    rand.nextInt(w) - off
  }
  
  def createController: Controller = {
    val behav = new Behaviour() {
      def apply(sens: Sensors, player: Player): Unit = {
        val vp = player.asInstanceOf[VsocPlayer]
        val pos = vp.getPosition
        val dir = vp.getDirection
        println("" + pos + dir + sens.getFlagsCenter + sens.getFlagsLeft + sens.getFlagsRight)
        player.move(ran(-60, 60), ran(-50, 50))
        player.turn(ran(0, 360))
      }
      def getChild(): Optional[Behaviour] = Optional.empty()
      def shouldBeApplied(sens: Sensors): Boolean = true
    }
    new BehaviourController(behav)
  }

  getContentPane.add(new FieldContentPanel(createServer))

  addWindowListener(this)

  def windowActivated(evt: WindowEvent): Unit = ()
  def windowClosed(evt: WindowEvent): Unit = ()
  def windowClosing(evt: WindowEvent): Unit = System.exit(0)
  def windowDeactivated(evt: WindowEvent): Unit = ()
  def windowDeiconified(evt: WindowEvent): Unit = ()
  def windowIconified(evt: WindowEvent): Unit = ()
  def windowOpened(evt: WindowEvent): Unit = ()

}

class FieldContentPanel(sim: Server) extends JPanel with ActionListener {

  setLayout(new BorderLayout())
  val field = new FieldPanel()
  field.setSim(sim)

  val ctrlPanel = new JPanel()
  ctrlPanel.setLayout(new FlowLayout())

  val takeStepButton = new JButton("take step")
  takeStepButton.addActionListener(this)

  ctrlPanel.add(takeStepButton)

  add(field, BorderLayout.CENTER)
  add(ctrlPanel, BorderLayout.SOUTH)

  def actionPerformed(evt: ActionEvent): Unit = {
    sim.takeStep()
  }

}