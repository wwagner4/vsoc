package playerpos

import vsoc.behaviour.Behaviour
import atan.model.Player
import vsoc.behaviour.Sensors
import java.util.Optional
import com.sun.security.sasl.ServerFactoryImpl
import vsoc.server.Server
import vsoc.behaviour.BehaviourController
import vsoc.server.ServerUtil
import scala.collection.JavaConversions._


object Main extends App {

  
  val behav = new Behaviour() {
    
    def shouldBeApplied(sensors: Sensors): Boolean = true
    
    def apply(sensors: Sensors,player: Player): Unit = {
      
      player.move(0, 10)
    }

    def getChild(): Optional[Behaviour] = Optional.empty()
    
  }
  
  val ctrl = new BehaviourController(behav)
  
  val srv = ServerUtil.current().createServer(10, 10)
  srv.getPlayers.foreach { p => p.setController(Playerpos.createController) }
  
  for (i <- (1 to 1000)) {
    srv.takeStep()
  }
}

