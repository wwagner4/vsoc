package vsoc.ga.trainga.ga

import vsoc.ga.trainga.config.{Config, WithConfigRunner}

object TrainGaMain extends  WithConfigRunner {

  def main(args: Array[String]): Unit = {
    runWithConfig(args, trainGa, TrainGaMain.getClass.getSimpleName)

    def trainGa(cfg: Config): Unit = {
      new ConfigRunner().run(cfg)
    }
  }



}
