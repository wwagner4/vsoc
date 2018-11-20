package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable
import vsoc.ga.genetic.{Geno, PopGeno}
import vsoc.ga.matches.Team

/**
  * Trainer for Teams
  *
  * @tparam S type of populationScore
  */
trait TrainGa[S] extends Describable {

  var iterations: Option[Int] = Option.empty

  var population: Option[PopGeno[Double]] = Option.empty

  var listeners: Seq[TrainGaListener[S]] = Seq.empty[TrainGaListener[S]]

  def id: String

  def teamsFromGeno(geno: Seq[Geno[Double]]): Seq[Team]

  def run(trainGaId: String, trainGaNr: String): Unit

}
