package vsoc.ga.common.persist.impl

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.nio.file.{Files, Path}

import vsoc.ga.common.persist.Persistor


case class PersistorNio(absolute: Path) extends Persistor {

  if (!absolute.isAbsolute) throw new IllegalArgumentException(s"'$absolute' must be absoluet")

  def save(path: Path)(f: ObjectOutputStream => Unit): Unit = {
    if (path.isAbsolute)  throw new IllegalArgumentException(s"'$path' must be relative")
    val filePath = absolute.resolve(path)
    if (!Files.exists(filePath.getParent)) Files.createDirectories(filePath.getParent)
    val os = Files.newOutputStream(filePath)
    try {
      val oos = new ObjectOutputStream(os)
      f(oos)
    } finally {
      os.close()
    }
  }

  def load[T](path: Path)(f: ObjectInputStream => T): T = {
    if (path.isAbsolute)  throw new IllegalArgumentException(s"'$path' must be relative")
    val filePath = absolute.resolve(path)
    if (!Files.exists(filePath.getParent)) throw new IllegalArgumentException(s"'$filePath' must exist")
    val is = Files.newInputStream(filePath)
    try {
      val ois = new ObjectInputStream(is)
      f(ois)
    } finally {
      is.close()
    }
  }
}
