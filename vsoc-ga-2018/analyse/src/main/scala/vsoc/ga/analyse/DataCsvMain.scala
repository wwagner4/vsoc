package vsoc.ga.analyse

import vsoc.ga.common.config.Configs

object DataCsvMain extends App {

  new Data02Csv().createCsvConfig(Configs.workB01)

}
