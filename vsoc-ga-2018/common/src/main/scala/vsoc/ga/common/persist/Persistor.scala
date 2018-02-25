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

  /**
    * Returns a directory of the persistor.
    * The directory is created if it does not exist
    * path must be relative
    */
  def dir(path: Path): Path

}
