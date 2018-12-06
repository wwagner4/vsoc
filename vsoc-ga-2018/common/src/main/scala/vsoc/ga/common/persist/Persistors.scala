package vsoc.ga.common.persist

import vsoc.ga.common.persist.impl.PersistorNio

object Persistors {

  def nio: Persistor = PersistorNio()

}
