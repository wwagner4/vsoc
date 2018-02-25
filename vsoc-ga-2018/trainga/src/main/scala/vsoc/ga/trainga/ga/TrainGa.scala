package vsoc.ga.trainga.ga

/**
  * Trainer for Teams
  * @tparam S type of score
  */
trait TrainGa[S] {

  var iterations: Option[Int] = Option.empty

  var population: Option[Seq[Seq[Double]]] = Option.empty

  var listeners: Seq[TrainGaListener[S]] = Seq.empty[TrainGaListener[S]]

  def id: String

  def run(trainGaId: String, trainGaNr: String): Unit

}
