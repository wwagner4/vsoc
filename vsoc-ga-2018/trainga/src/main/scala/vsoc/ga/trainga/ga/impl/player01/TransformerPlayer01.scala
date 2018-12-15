package vsoc.ga.trainga.ga.impl.player01

import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.Transformer

class TransformerPlayer01 extends Transformer[Double, PhenoPlayer01] {

  override def toPheno(geno: Seq[Double]): PhenoPlayer01 = {

    def behav: Behaviour = ???

    PhenoPlayer01(geno, behav)
  }


}
