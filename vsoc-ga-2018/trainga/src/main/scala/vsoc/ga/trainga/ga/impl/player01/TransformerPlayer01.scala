package vsoc.ga.trainga.ga.impl.player01

import vsoc.ga.genetic.Transformer

class TransformerPlayer01 extends Transformer[Double, PhenoPlayer01] {

  override def toPheno(geno: Seq[Double]): PhenoPlayer01 = PhenoPlayer01(geno)

}
