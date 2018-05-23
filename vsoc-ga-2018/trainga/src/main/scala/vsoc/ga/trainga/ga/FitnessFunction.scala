package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable
import vsoc.ga.matches.TeamResult

trait FitnessFunction extends Describable {

  def id: String

  def fitness(result: TeamResult): Double

}
