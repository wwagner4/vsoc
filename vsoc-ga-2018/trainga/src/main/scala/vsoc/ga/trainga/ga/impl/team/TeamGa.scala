package vsoc.ga.trainga.ga.impl.team

import vsoc.ga.matches.Team
import vsoc.ga.trainga.nn.NeuralNet
import vsoc.ga.genetic._


trait TeamGa extends Pheno[Double] {

  def neuralNets: Seq[NeuralNet]

  def vsocTeam: Team

}
