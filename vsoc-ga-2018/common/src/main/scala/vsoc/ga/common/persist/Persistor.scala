package vsoc.ga.common.persist

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.nio.file.Path

trait Persistor {

  /**
    * Saves an object to the relative path
    */
  def save(path: Path)(f: ObjectOutputStream => Unit): Unit

  /**
    * Loads an object if the relative path exists
    */
  def load[T](path: Path)(f: ObjectInputStream => T): Option[T]

}
