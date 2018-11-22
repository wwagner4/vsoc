package vsoc.ga.trainga.ga

import vsoc.ga.common.commandline.WithConfigRunner
import vsoc.ga.common.config.Config

object TrainGaMain extends  WithConfigRunner {

  def main(args: Array[String]): Unit = {
    runWithConfig(args, trainGa, TrainGaMain.getClass.getSimpleName)

    def trainGa(cfg: Config): Unit = {
      new ConfigRunner().run(cfg)
  }
  }



}
