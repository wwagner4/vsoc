package vsoc.ga.trainga.thinner

import vsoc.ga.common.commandline.WithPathRunner

object ThinnerMain extends App with WithPathRunner{

  runWithPath(args, Thinner.thinFromDirTree, ThinnerMain.getClass.getName)

}
