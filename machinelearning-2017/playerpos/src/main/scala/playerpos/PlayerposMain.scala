package playerpos

import vsoc.behaviour.Behaviour
import atan.model.Player
import vsoc.behaviour.Sensors
import java.util.Optional
import com.sun.security.sasl.ServerFactoryImpl
import vsoc.server.Server
import vsoc.behaviour.BehaviourController
import vsoc.server.ServerUtil

object Main extends App {

  
  val behav = new Behaviour() {
    
    def shouldBeApplied(sensors: Sensors): Boolean = true
    
    def apply(sensors: Sensors,player: Player): Unit = {
      
      player.move(0, 10)
    }

    def getChild(): Optional[Behaviour] = Optional.empty()
    
  }
  
  val ctrl = new BehaviourController(behav)
  
  val srv = ServerUtil.current().createServer(1, 0)
  srv.getPlayersEast.get(0).setController(ctrl)
  
  
  srv.takeStep()
  srv.takeStep()
  srv.takeStep()
  
  println("finished")
}

