package vsoc.ga.trainga.ga

import java.nio.file.Path
import java.util.concurrent.Executors

import vsoc.ga.common.commandline.WithConfigRunner
import vsoc.ga.common.config.Config

object TrainGaMain extends  WithConfigRunner {

  def main(args: Array[String]): Unit = {
    runWithConfig(args, trainGa, TrainGaMain.getClass.getSimpleName)

    def trainGa(cfg: Config, wdBase: Path): Unit = {
      val ec = Executors.newFixedThreadPool(cfg.trainings.size)
      for (c <- cfg.trainings) {
        ec.execute(() => TrainGaRunner.run(wdBase, c))
      }
    }
  }



}
