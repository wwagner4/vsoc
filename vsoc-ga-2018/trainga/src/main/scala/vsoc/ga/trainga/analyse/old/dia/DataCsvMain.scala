package vsoc.ga.trainga.analyse.old.dia

import vsoc.ga.trainga.config.Configs

object DataCsvMain extends App {

  new Data02Csv().createCsvConfig(Configs.b02Bob)

}
