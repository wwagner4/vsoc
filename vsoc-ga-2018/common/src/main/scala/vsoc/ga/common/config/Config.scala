package vsoc.ga.common.config

import java.nio.file.Path

import vsoc.ga.common.describe.Describable

trait Config extends Describable {

  /**
    * @return The directory where all the results are stored
    */
  def workDirBase: Path

  /**
    * @return List of training configurations
    */
  def trainings: Seq[ConfigTrainGa]

  /**
    * @return Must be the same as the method name in Cinfigs
    */
  def id: String

}
