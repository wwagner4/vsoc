package vsoc.ga.trainga.ga.impl

import vsoc.ga.trainga.ga.OutputFactors

/**
  * Base for ne tests.
  * The parameters of this class are equal to trainGa01ofM2
  */
abstract class TrainGa04Abstract extends TrainGa01Abstract {

  override def outputFactors: OutputFactors = OutputFactors(50.0, 20.0, 5.0)

  override def fullDesc: String =
    """Output factors 50 20 5
    """.stripMargin

}
