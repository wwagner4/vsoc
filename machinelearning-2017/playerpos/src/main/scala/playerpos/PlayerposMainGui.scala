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

object PlayerposMainGui extends App {

}

class FieldFrame extends JFrame with WindowListener {
  addWindowListener(this)
  
  
  
  def windowActivated(evt: WindowEvent): Unit = ()
  def windowClosed(evt: WindowEvent): Unit = ()
  def windowClosing(evt: WindowEvent): Unit = System.exit(0)
  def windowDeactivated(evt: WindowEvent): Unit = ()
  def windowDeiconified(evt: WindowEvent): Unit = ()
  def windowIconified(evt: WindowEvent): Unit = ()
  def windowOpened(evt: WindowEvent): Unit = ()
  
}

class FieldContentPanel extends JPanel with ActionListener {
  
  setLayout(new BorderLayout())
  val fieldCanvas = new FieldCanvas()

  val ctrlPanel = new JPanel()
  ctrlPanel.setLayout(new FlowLayout())
  
  val takeStepButton = new JButton("take step")
  takeStepButton.addActionListener(this)
  
  ctrlPanel.add(takeStepButton)
  
  add(fieldCanvas, BorderLayout.CENTER)
  add(ctrlPanel, BorderLayout.SOUTH)
  
  def actionPerformed(evt: ActionEvent): Unit = ???
  
  
  
}