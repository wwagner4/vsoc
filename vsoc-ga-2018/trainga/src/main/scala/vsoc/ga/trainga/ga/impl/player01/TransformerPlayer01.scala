package vsoc.ga.trainga.ga.impl.player01

import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.Transformer
import vsoc.ga.trainga.behav.{InputMappers, OutputMappers}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNets}

class TransformerPlayer01(net: NeuralNet) extends Transformer[Double, PhenoPlayer01] {

  override def toPheno(geno: Seq[Double]): PhenoPlayer01 = {

    def behav: Behaviour = {
      val out = OutputMappers.om02
      val in = InputMappers.default
      val child = java.util.Optional.of(vsoc.ga.matches.Behaviours.remainOnField)
      new vsoc.ga.trainga.behav.BehaviourNeuralNet(net, child, in, out)
    }

    PhenoPlayer01(geno, behav)
  }


}
