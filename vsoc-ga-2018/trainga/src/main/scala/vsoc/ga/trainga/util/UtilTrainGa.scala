package vsoc.ga.trainga.util

import java.nio.file.{Files, Path}

object UtilTrainGa {

  def configureLogfile(workDirBase: Path): Unit = {
    val p = workDirBase.resolve("logs")
    Files.createDirectories(p)
    val f = p.resolve(s"vsoc-ga-2018.log")
    System.setProperty("logfile.name", f.toString)
    println(s"Writing log to $f")
  }

}
