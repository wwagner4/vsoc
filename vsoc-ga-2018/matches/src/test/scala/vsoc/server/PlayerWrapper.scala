package vsoc.server

case class PlayerWrapper(player: VsocPlayer) {


  def incEastGoalCount(): Unit = player.increaseEastGoalCount()
  def incWestGoalCount(): Unit = player.increaseWestGoalCount()
  def incKickCount(): Unit = player.increaseKickCount()
  def incKickOutCount(): Unit = player.increaseKickOutCount()

}
