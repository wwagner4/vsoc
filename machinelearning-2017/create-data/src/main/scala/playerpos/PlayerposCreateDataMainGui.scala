package playerpos

import java.awt._
import java.awt.event._
import javax.swing._

import vsoc.server.{Server, ServerUtil}
import vsoc.server.gui.FieldPanel

import scala.collection.JavaConverters._

object PlayerposCreateDataMainGui extends App {

  val f = new FieldFrame()
  f.setSize(900, 600)
  f.setVisible(true)

}

class FieldFrame extends JFrame with WindowListener {

  import PlayerposCreateData._
  import Placement._

  def createServer: Server = {
    val server = ServerUtil.current().createServer(1, 0)
    for (p <- server.getPlayers.asScala) {
      p.setController(createController(None, placeControllerRandomPos))
    }
    server
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
