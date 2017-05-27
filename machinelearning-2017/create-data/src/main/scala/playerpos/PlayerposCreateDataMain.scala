package playerpos

object PlayerposCreateDataMain extends App {

  import Placement._

  val sizes: Seq[Int] = List(1000, 5000, 10000, 50000)


  PlayerposCreateData.createDataFiles("random_walk_from_center", sizes, placeControllerRandomWalkFromCenter)

}

