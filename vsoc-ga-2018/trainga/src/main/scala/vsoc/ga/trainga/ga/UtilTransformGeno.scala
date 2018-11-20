package vsoc.ga.trainga.ga

import vsoc.ga.genetic.Geno

object UtilTransformGeno {

  def toSeqGeno[A](popArray: Array[Array[A]]): Seq[Geno[A]] = {
    popArray.map(a => Geno(a.toSeq)).toSeq
  }

  def asArrayGenoDouble(pop: Seq[Geno[Double]]): Array[Array[Double]] = {
    pop.map(g => g.genos.toArray).toArray
  }

}
