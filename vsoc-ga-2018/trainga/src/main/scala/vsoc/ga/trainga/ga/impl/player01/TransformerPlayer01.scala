package vsoc.ga.trainga.ga.impl.player01

import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.Transformer
import vsoc.ga.matches.behav.{BehaviourNeuralNet, InputMappers, OutputMappers}
import vsoc.ga.matches.nn.{NeuralNetFactory, NeuralNets}

class TransformerPlayer01(nnf: NeuralNetFactory) extends Transformer[Double, PhenoPlayer01] {

  override def toPheno(geno: Seq[Double]): PhenoPlayer01 = {

    def behav: Behaviour = {
      val out = OutputMappers.om02
      val in = InputMappers.default
      val child = java.util.Optional.of(vsoc.ga.matches.Behaviours.remainOnField)
      val net = nnf.neuralNet
      net.setParam(geno.toArray)
      new BehaviourNeuralNet(net, child, in, out)
    }

    PhenoPlayer01(geno, behav)
  }


}
