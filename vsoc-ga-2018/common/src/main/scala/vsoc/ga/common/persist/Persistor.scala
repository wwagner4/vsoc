package vsoc.ga.common.persist

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.nio.file.Path

trait Persistor {

  def save(path: Path)(f: ObjectOutputStream => Unit): Unit

  def load[T](path: Path)(f: ObjectInputStream => T): T

}
