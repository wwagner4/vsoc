package vsoc.ga.trainga.commandline

import java.nio.file.{Path, Paths}

import vsoc.ga.common.UtilReflection
import vsoc.ga.trainga.ga.{TrainGa, TrainGas}

trait WithPathTrainGaNrRunner {

  /**
    *
    * @param args  Arguments from the commandline
    * @param f     The method to be called
    * @param clazz Name of the class calling this. Needed to create meaningful error messages
    */
  def runWithArgs(args: Array[String], f: (Path, TrainGa[Double], String, Option[String]) => Unit, clazz: String): Unit = {

    def usage: String = {
      s"""usage ...$clazz <configId>
         | - baseWorkDir: Base directory where all the populations are stored
         | - configId: TrainGa ID. One of the method defined in TrainGas.
         | - nr: One of the Numbers defined for the id
         |""".stripMargin
    }

    def call(
              workDirString: String,
              trainGaId: String,
              trainGaNr: String,
              popNr: Option[String]): Unit = {
      try {
        val trainGa = UtilReflection.call(TrainGas, trainGaId, classOf[TrainGa[Double]])
        val workDirPath = Paths.get(workDirString)
        f(workDirPath, trainGa, trainGaNr, popNr)
      } catch {
        case e: ScalaReflectionException =>
          println(s"Invalid configuration '$trainGaId'")
          println(usage)
      }
    }

    if (args.length == 3) {
      call(args(0), args(1), args(2), None)
    } else if (args.length == 4) {
      call(args(0), args(1), args(2), Some(args(3)))
    } else {
      println(usage)
    }
  }

}
