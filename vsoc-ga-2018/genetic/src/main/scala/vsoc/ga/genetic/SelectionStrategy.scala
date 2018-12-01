package vsoc.ga.genetic

import vsoc.ga.common.describe.Describable

trait SelectionStrategy[A] extends Describable {

  def select(tested: Seq[(Double, Seq[A])]): Seq[Seq[A]]

}
