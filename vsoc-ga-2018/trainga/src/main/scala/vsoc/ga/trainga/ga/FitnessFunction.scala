package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable

trait FitnessFunction[T] extends Describable {

  def id: String

  def fitness(result: T): Double

}
