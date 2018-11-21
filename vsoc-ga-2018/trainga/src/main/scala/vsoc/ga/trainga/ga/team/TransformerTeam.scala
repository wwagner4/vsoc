package vsoc.ga.trainga.ga.team

import java.util.Optional

import vsoc.behaviour.Behaviour
import vsoc.ga.genetic.{Geno, Transformer}
import vsoc.ga.matches.{Behaviours, Team, Teams}
import vsoc.ga.trainga.behav.{BehaviourNeuralNet, InputMapperNn, OutputMapperNn}
import vsoc.ga.trainga.nn.NeuralNet

class TransformerTeam(playerCount: Int, createNeuralNet: () => NeuralNet, _in: InputMapperNn, _out: OutputMapperNn) extends Transformer[Double, TeamGa] {

  override def toPheno(geno: Geno[Double]): TeamGa = {

    def behav(nn: NeuralNet): Behaviour = {
      val in: InputMapperNn = _in
      val out: OutputMapperNn = _out

      new BehaviourNeuralNet(nn, Optional.of(Behaviours.remainOnField), in, out)
    }

    def nn(param: Seq[Double]): NeuralNet = {
      val nn = createNeuralNet()
      nn.setParam(param.toArray)
      nn
    }

    val grpSize = geno.alleles.size / playerCount
    val nns: Seq[NeuralNet] = geno.alleles
      .grouped(grpSize)
      .toSeq
      .map(nn)
    val behavs = nns.map(behav)
    val team = Teams.behaviours(behavs, "undefined")
    new TeamGa {
      override def vsocTeam: Team = team

      override def toString: String = team.name

      override def neuralNets: Seq[NeuralNet] = nns
    }
  }

  override def toGeno(pheno: TeamGa): Geno[Double] = {
    val nns: Seq[NeuralNet] = pheno.neuralNets
    val gseq = nns.flatMap(nn => nn.getParam)
    Geno(gseq)
  }
}
