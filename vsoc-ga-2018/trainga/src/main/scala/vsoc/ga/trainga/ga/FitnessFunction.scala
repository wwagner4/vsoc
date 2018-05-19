package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable
import vsoc.ga.matches.TeamResult

trait FitnessFunction extends Describable {

  def fitness(result: TeamResult): Double

}
