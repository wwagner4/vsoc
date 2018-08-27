package vsoc.ga.analyse

trait DiaFactories[T] {

  def trainGaId: String

  def diaFactories: Seq[DiaFactory[T]]

}
