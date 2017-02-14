package playerpos

import vsoc.behaviour.Sensors
import vsoc.server.Server

object Main extends App {

  val f = new VsocFactory()
  
  val s = f.server
  val p = f.player
  
  val pos = Pos(10, 20, 222)
  
  s.setPlayer(p, pos)
  s.takeOneStep()
  
  val sensors = p.sensors
  
  
  
}

class VsocFactory {
  
  def server: Server = new Server {
    
    val s = new vsoc.server.Server()
    
    def setPlayer(p: Player,pas: Pos): Unit = ??? 
    
    def takeOneStep(): Unit = ???
    
  }
  
  def player: Player = new Player {
      def sensors: Sensors = ???
  }
  
}

case class Pos(x: Double, y: Double, dir: Double)


trait Server {

  def takeOneStep()
  
  def setPlayer(p: Player, pas: Pos)
  
}

trait Player {
  
  def sensors: Sensors
  
}