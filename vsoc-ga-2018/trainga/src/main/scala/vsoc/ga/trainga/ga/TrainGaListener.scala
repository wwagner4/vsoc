package vsoc.ga.trainga.ga

trait TrainGaListener[S] {

  def onIterationFinished(iteration: Int, score: Option[S], data: Seq[(String, Any)])

}
