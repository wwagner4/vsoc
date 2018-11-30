package vsoc.ga.analyse.old.dia

import vsoc.ga.common.config.Configs

object DataCsvMain extends App {

  new Data02Csv().createCsvConfig(Configs.b02Bob)

}
