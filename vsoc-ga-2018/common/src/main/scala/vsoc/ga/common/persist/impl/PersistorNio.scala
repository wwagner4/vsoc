package vsoc.ga.common.persist.impl

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.nio.file.{Files, Path}

import vsoc.ga.common.persist.Persistor


case class PersistorNio() extends Persistor {

  def save(path: Path)(f: ObjectOutputStream => Unit): Unit = {
    if (!path.isAbsolute)  throw new IllegalArgumentException(s"'$path' must be absolute")
    if (!Files.exists(path.getParent)) Files.createDirectories(path.getParent)
    val os = Files.newOutputStream(path)
    try {
      val oos = new ObjectOutputStream(os)
      f(oos)
    } finally {
      os.close()
    }
  }

  def load[T](path: Path)(f: ObjectInputStream => T): Option[T] = {
    if (!path.isAbsolute)  throw new IllegalArgumentException(s"'$path' must be absolute")
    if (!Files.exists(path)) None
    else {
      val is = Files.newInputStream(path)
      try {
        val ois = new ObjectInputStream(is)
        Some(f(ois))
      } finally {
        is.close()
      }
    }
  }

}
