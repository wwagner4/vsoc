package vsoc.ga.trainga.commandline

import vsoc.ga.common.UtilReflection
import vsoc.ga.trainga.ga.{TrainGa, TrainGas}

trait WithPathTrainGaNrRunner {

  /**
    *
    * @param args  Arguments from the commandline
    * @param f     The method to be called
    * @param clazz Name of the class calling this. Needed to create meaningful error messages
    */
  def runWithArgs(args: Array[String], f: (TrainGa[Double], String, Option[String]) => Unit, clazz: String): Unit = {

    def usage: String = {
      s"""usage ...$clazz <trainGa> <populationNr> [<generationNr>]
         | - trainGa: TrainGa ID. One of the method defined in TrainGas. E.g. trainGa01, ...
         | - populationNr: Population Nr. E.g. bob001, w001, ...
         | - generationNr: Generation Number. If no <generationNr> is defined the latest is selcted
         |""".stripMargin
    }

    def call(
              trainGaId: String,
              popNr: String,
              generationNr: Option[String]): Unit = {
      try {
        val trainGa = UtilReflection.call(TrainGas, trainGaId, classOf[TrainGa[Double]])
        f(trainGa, popNr, generationNr)
      } catch {
        case e: ScalaReflectionException =>
          println(s"Invalid configuration '$trainGaId'")
          println(usage)
      }
    }

    if (args.length == 2) {
      call(args(0), args(1), None)
    } else if (args.length == 3) {
      call(args(0), args(1), Some(args(2)))
    } else {
      println(usage)
    }
  }

}
