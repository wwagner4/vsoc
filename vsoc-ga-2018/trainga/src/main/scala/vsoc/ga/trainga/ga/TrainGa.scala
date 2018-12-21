package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable
import vsoc.ga.matches.Team

/**
  * Trainer for Teams
  *
  * @tparam S type of populationScore
  */
trait TrainGa[S] extends Describable {

  def id: String

  var iterations: Int = 0

  var population: Seq[Seq[Double]] = Seq.empty[Seq[Double]]

  var listeners: Seq[TrainGaListener[S]] = Seq.empty[TrainGaListener[S]]

  def teamsFromPopulation: Seq[Team]

  def run(trainGaId: String, trainGaNr: String): Unit

}
