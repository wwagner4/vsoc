package playerpos

import vsoc.camps.VectorFunction
import vsoc.camps.AbstractCamp
import vsoc.camps.Member
import vsoc.camps.VectorFunctionBehaviour
import vsoc.camps.SensorsToVector
import vsoc.camps.VectorFunctionBehaviourController
import vsoc.server.VsocPlayer

object Main extends App {

}

class PlayerposCamp extends AbstractCamp[Member[VectorFunctionBehaviourController[PlayerposVectorFunction]], PlayerposVectorFunction] {
  def createNextGeneration(): Unit = ???
  def eastPlayerCount(): Int = ???
  def getMembers(): java.util.List[vsoc.camps.Member[vsoc.camps.VectorFunctionBehaviourController[PlayerposVectorFunction]]] = ???
  def initPlayersForMatch(): Unit = ???
  def updateMemberFromPlayer(player: VsocPlayer, mem: Member[VectorFunctionBehaviourController[PlayerposVectorFunction]]): Unit = ???
  def westPlayerCount(): Int = ???

}

class PlayerposBehaviour(vf: PlayerposVectorFunction, stv: SensorsToVector) extends VectorFunctionBehaviour[PlayerposVectorFunction](vf, stv) {

}

class PlayerposVectorFunction extends VectorFunction {
  
  	def apply(in: Array[Double]): Array[Double] = ???


}