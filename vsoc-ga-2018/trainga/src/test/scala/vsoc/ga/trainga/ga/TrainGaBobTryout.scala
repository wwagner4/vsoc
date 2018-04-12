package vsoc.ga.trainga.ga

import java.nio.file.Paths

object TrainGaBobTryout extends App {

  val wdir = Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018")

  TrainGaMain.main(Array("bob009", wdir.toString))

}
