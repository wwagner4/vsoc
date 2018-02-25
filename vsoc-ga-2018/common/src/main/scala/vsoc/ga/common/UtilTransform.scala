package vsoc.ga.common

import java.util.Optional

object UtilTransform {

  def asOption[T](in: Optional[T]): Option[T] = if (in.isPresent) Some(in.get()) else None

  def toSeq[T](in: Array[Array[T]]): Seq[Seq[T]] = {
    in.toSeq.map(a => a.toSeq)
  }

  def asArray(in: Seq[Seq[Double]]): Array[Array[Double]] = {
    in.toArray.map(s => s.toArray)
  }

}
