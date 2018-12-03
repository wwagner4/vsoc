package vsoc.ga.trainga.ga

import vsoc.ga.common.describe.Describable
import vsoc.ga.genetic._


trait FitnessFunction1[T <: Score[T]] extends FitnessFunction[T] with Describable {

  def id: String

}




