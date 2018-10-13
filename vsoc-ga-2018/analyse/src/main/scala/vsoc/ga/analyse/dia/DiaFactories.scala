package vsoc.ga.analyse.dia

import vsoc.ga.analyse.dia.DataDia.DIA

trait DiaFactories[T] {

  def trainGaId: String

  def diaFactories: Seq[DIA[T]]

}
