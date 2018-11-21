package vsoc.ga.trainga.ga.team

import vsoc.ga.matches.Team
import vsoc.ga.trainga.nn.NeuralNet
;

trait TeamGa {

  def neuralNets: Seq[NeuralNet]

  def vsocTeam: Team

}
