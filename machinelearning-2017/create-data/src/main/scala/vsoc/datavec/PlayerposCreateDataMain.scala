package vsoc.datavec

object PlayerposCreateDataMain extends App {

  import Placement._

  val sizes: Seq[Int] = List(10, 100, 1000, 50000, 200000, 500000, 1000000)


  PlayerposCreateData.createDataFiles("random_pos", sizes, placeControllerRandomPos, new InitialPlacementRandomPos(1))

}

