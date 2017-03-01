package playerpos

/**
  * Created by wwagner4 on 28/02/2017.
  */
object PlayerPosLinRegTryout extends App {

  val (x, y) = PlayerposLinReg.readDataFile(common.Util.dataFile("pos04.txt"))

  println(s"x\n$x")
  println(s"y\n$y")
}
