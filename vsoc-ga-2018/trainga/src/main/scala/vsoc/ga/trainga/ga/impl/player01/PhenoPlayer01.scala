package vsoc.ga.trainga.ga.impl.player01

import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.Pheno

case class PhenoPlayer01(geno: Seq[Double],
                         behav: Behaviour,
                        ) extends Pheno[Double]

