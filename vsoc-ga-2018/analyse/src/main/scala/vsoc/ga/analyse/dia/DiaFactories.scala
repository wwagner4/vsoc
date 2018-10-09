package vsoc.ga.analyse.dia

trait DiaFactories[T] {

  def trainGaId: String

  def diaFactories: Seq[DiaFactory[T]]

}
