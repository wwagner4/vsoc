package vsoc.ga.trainga.ga.impl

/**
  * Base for ne tests.
  * The parameters of this class are equal to trainGa01ofM2
  */
abstract class TrainGa04Abstract extends TrainGa01Abstract {

  override def outputFactors: OutputFactors = OutputFactors(50.0, 20.0, 5.0)

}
