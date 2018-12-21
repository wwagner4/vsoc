package vsoc.ga.common

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator
import java.util.zip.ZipInputStream

import org.apache.commons.compress.archivers.sevenz.SevenZFile

object UtilPath {

  def unzip(zipFile: Path, outDir: Path): Unit = {
    UtilTryWithResource.withResources(new ZipInputStream(Files.newInputStream(zipFile))) { zipInputStream =>
      var entry = zipInputStream.getNextEntry
      while (entry != null) {
        val toPath = outDir.resolve(entry.getName)
        if (entry.isDirectory) Files.createDirectory(toPath)
        else Files.copy(zipInputStream, toPath)
        entry = zipInputStream.getNextEntry
      }
    }
  }

  def un7z(_7zPath: Path, outDir: Path): Unit = {
    val _7zFile = new SevenZFile(_7zPath.toFile)
    var entry = _7zFile.getNextEntry
    while (entry != null) {
      if (!entry.isDirectory) {
        val curfile = outDir.resolve(entry.getName)
        val parent = curfile.getParent
        if (!Files.exists(parent)) {
          Files.createDirectories(parent)
        }
        UtilTryWithResource.withResources(Files.newOutputStream(curfile)) { out =>
          val content = Array.ofDim[Byte](entry.getSize.toInt)
          _7zFile.read(content, 0, content.length)
          out.write(content)
        }
      }
      entry = _7zFile.getNextEntry
    }
  }

  def delDirectory(rootPath: Path): Unit = {
    Files.walk(rootPath)
      .sorted(Comparator.reverseOrder())
      .forEach(f => Files.delete(f))
  }

  def homeDir: Path = Paths.get(System.getProperty("user.home"))

  def tmpDir: Path = Paths.get(System.getProperty("java.io.tmpdir"))


}
