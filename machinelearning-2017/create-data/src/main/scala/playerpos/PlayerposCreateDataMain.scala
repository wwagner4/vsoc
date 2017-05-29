package playerpos

object PlayerposCreateDataMain extends App {

  import Placement._

  val sizes: Seq[Int] = List(10)


  PlayerposCreateData.createDataFiles("random_pos", sizes, placeControllerRandomPos)

}

