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
import scala.collection.JavaConverters._
import vsoc.behaviour.SensorsToVector
import atan.model.Flag
import java.util.Arrays
import common.FlagDirectionSensToVector
import common.SensToVec
import vsoc.server.gui.FieldPanel
import vsoc.behaviour.BehaviourController
import vsoc.server.VsocPlayer
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import vsoc.server.ServerUtil
import vsoc.server.Server
import vsoc.server.gui.FieldPanel
import vsoc.behaviour.BehaviourController
import vsoc.server.VsocPlayer
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import vsoc.server.ServerUtil
import vsoc.server.Server
import vsoc.server.gui.FieldPanel
import vsoc.behaviour.BehaviourController
import vsoc.server.VsocPlayer
import vsoc.behaviour.Behaviour
import vsoc.behaviour.Sensors
import vsoc.server.ServerUtil
import vsoc.util.Vec2D
import common.Formatter

object PlayerposMainGui extends App {

  val f = new FieldFrame()
  f.setSize(900, 600)
  f.setVisible(true)

}

class FieldFrame extends JFrame with WindowListener {

  def createServer: Server = {
    val s = ServerUtil.current().createServer(1, 0)
    for (p <- s.getPlayers.asScala) {
      p.setController(Playerpos.createController(None))
    }
    s
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
