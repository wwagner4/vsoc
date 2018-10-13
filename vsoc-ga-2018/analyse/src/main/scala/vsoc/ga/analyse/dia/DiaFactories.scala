package vsoc.ga.analyse.dia

import vsoc.ga.analyse.dia.DataDia.FDia

trait DiaFactories[T] {

  def trainGaId: String

  def diaFactories: Seq[FDia[T]]

}
