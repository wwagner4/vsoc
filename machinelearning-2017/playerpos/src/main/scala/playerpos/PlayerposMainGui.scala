package playerpos

import atan.model.Player
import atan.model.Controller

import vsoc.server.Server
import vsoc.server.VsocPlayer
import vsoc.server.ServerUtil
import vsoc.server.gui.FieldPanel
import vsoc.behaviour.BehaviourController
import vsoc.behaviour.Behaviour

import vsoc.behaviour.Sensors
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.WindowListener
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.JButton
import javax.swing.JPanel
import java.util.Optional

import scala.util.Random
import scala.collection.JavaConversions._


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
    from + rand.nextInt(w)
  }

  def createController: Controller = {
    val behav = new Behaviour() {

      var cnt = 0

      def apply(sens: Sensors, player: Player): Unit = {
        if (cnt % 20 == 0) player.move(ran(-20, 20), ran(-20, 20))
        cnt += 1
        val vp = player.asInstanceOf[VsocPlayer]
        val pos = vp.getPosition
        val dir = vp.getDirection
        println("" + pos + dir + sens.getFlagsCenter + sens.getFlagsLeft + sens.getFlagsRight)
        player.dash(ran(50, 300))
        player.turn(ran(-30, 30))
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
  
  val field = new FieldPanel()
  field.setSim(sim)

  val takeStepButton = new JButton("take step")
  takeStepButton.addActionListener(this)

  val ctrlPanel = new JPanel()
  ctrlPanel.setLayout(new FlowLayout())
  ctrlPanel.add(takeStepButton)

  this.setLayout(new BorderLayout())
  this.add(field, BorderLayout.CENTER)
  this.add(ctrlPanel, BorderLayout.SOUTH)

  def actionPerformed(evt: ActionEvent): Unit = {
    sim.takeStep()
  }

}