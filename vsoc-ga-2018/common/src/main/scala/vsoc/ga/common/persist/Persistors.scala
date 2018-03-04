package vsoc.ga.common.persist

import java.nio.file.Path

import vsoc.ga.common.persist.impl.PersistorNio

object Persistors {

  def nio(baseDir: Path): Persistor = PersistorNio(baseDir)

}
