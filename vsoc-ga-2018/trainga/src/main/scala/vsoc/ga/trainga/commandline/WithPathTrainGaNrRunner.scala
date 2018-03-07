package vsoc.ga.trainga.commandline

import java.nio.file.{Path, Paths}

import vsoc.ga.common.UtilReflection
import vsoc.ga.trainga.ga.{TrainGa, TrainGas}

trait WithPathTrainGaNrRunner {

  /**
    *
    * @param _args Arguments from the commandline
    * @param f The method to be called
    * @param clazz Name of the class calling this. Needed to create meaningful error messages
    */
  def runWithArgs(_args: Array[String], f: (Path, TrainGa[Double], String) => Unit, clazz: String): Unit = {
    if (_args.length != 3) {
      println(usage(clazz))
    } else {
      val pathString = _args(0)
      val trainGaId = _args(1)
      val nr = _args(2)
      try {
        val trainGa = UtilReflection.call(TrainGas, trainGaId, classOf[TrainGa[Double]])
        f(Paths.get(pathString), trainGa, nr)
      } catch {
        case e: ScalaReflectionException =>
          println(s"Invalid configuration '$trainGaId'")
          println(usage(clazz))
      }
    }
  }

  private def usage(clazz: String): String = {
    s"""usage ...$clazz <configId>
       | - baseWorkDir: Base directory where all the populations are stored
       | - configId: TrainGa ID. One of the method defined in TrainGas.
       | - nr: One of the Numbers defined for the id
       |""".stripMargin
  }


}
