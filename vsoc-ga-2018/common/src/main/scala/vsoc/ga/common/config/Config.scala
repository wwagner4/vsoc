package vsoc.ga.common.config

import java.nio.file.Path

trait Config {

  /**
    * @return The directory where all the results are stored
    */
  def workDirBase: Path

  /**
    * @return List of training configurations
    */
  def trainings: Seq[ConfigTrainGa]

}
