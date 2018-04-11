package vsoc.ga.trainga.thinner

import java.nio.file.{Files, Paths}

object ThinnerMainWorkTryout extends App {

  val wd = Paths.get("C:\\ta30\\entw1\\work")
  require(Files.exists(wd), "Workdir must exuist. " + wd.toString)

  ThinnerMain.main(Array(wd.toString))

}
