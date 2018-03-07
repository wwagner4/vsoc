package vsoc.ga.trainga.thinner

import vsoc.ga.trainga.thinner.impl.ThinnerIndex

object ThinnerIndexTryout extends App {

  val idx = Seq(1, 0)
  println(ThinnerIndex.thin(idx))

}
