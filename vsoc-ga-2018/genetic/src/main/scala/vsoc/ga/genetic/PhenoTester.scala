package vsoc.ga.genetic

import vsoc.ga.common.describe.Describable

trait PhenoTester[P, S <: Score[S]] extends Describable {

  def test(phenos: Seq[P]): PhenoTesterResult[P, S]

}

