package vsoc.ga.trainga.thinner

import vsoc.ga.common.config.ConfigHelper

object ThinnerMainTryout extends App {

  ThinnerMain.main(Array(s"${System.getProperty("user.home")}/work/work-vsoc-ga-2018"))
  // ThinnerMain.main(Array(ConfigHelper.defaultWorkDir.toString))

}
