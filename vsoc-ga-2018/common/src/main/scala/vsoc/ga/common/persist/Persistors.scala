package vsoc.ga.common.persist

import java.nio.file.{Path, Paths}

import vsoc.ga.common.persist.impl.PersistorNio

object Persistors {

  def nio(baseDir: Path): Persistor = PersistorNio(baseDir)

  def workDir: Persistor = PersistorNio(Paths.get(System.getProperty("user.home"), "work", "work-vsoc-ga-2018"))

}
