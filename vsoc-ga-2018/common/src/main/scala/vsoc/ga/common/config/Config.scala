package vsoc.ga.common.config

import vsoc.ga.common.describe.Describable

trait Config extends Describable {

  /**
    * @return List of training configurations
    */
  def trainings: Seq[ConfigTrainGa]

  /**
    * @return Must be the same as the method name in Configs
    */
  def id: String

}
