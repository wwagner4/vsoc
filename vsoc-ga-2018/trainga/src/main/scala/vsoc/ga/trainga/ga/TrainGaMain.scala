package vsoc.ga.trainga.ga

import vsoc.ga.common.commandline.WithConfigRunner
import vsoc.ga.common.config.Configs

object TrainGaMain extends  WithConfigRunner {

  def main(args: Array[String]): Unit = {
      new ConfigRunner().run(Configs.b05Test)
  }

}
