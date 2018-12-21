package vsoc.ga.trainga.ga.impl.team01

import vsoc.ga.genetic._
import vsoc.ga.matches.Team
import vsoc.ga.matches.nn.NeuralNet


trait PhenoTeam extends Pheno[Double] {

  def neuralNets: Seq[NeuralNet]

  def vsocTeam: Team

}
