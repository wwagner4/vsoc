package vsoc.ga.trainga.ga.impl

import scala.util.Random
;

object RandomElemsPicker {

  def pick[T](elems: Seq[T], ran: Random): (T, T) = {
    require(elems.size >= 2)
    val sh = ran.shuffle(elems)
    (sh(0), sh(1))
  }

}
