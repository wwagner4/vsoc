package vsoc.ga.trainga.ga

import java.util.concurrent.Executors

import vsoc.ga.trainga.config.Config

class ConfigRunner {

  def run(cfg: Config): Unit = {
    val ec = Executors.newFixedThreadPool(cfg.trainings.size)
    for (training <- cfg.trainings) {
      ec.execute(() => new TrainGaRunner().run(training))
    }
  }

}
