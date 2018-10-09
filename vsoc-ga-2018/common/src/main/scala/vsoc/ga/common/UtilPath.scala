package vsoc.ga.common

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator
import java.util.zip.ZipInputStream

import vsoc.ga.common.config.ConfigHelper

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

  def delDirectory(rootPath: Path): Unit = {
    Files.walk(rootPath)
      .sorted(Comparator.reverseOrder())
      .forEach(f => Files.delete(f))
  }

  def homeDir: Path = Paths.get(System.getProperty("user.home"))

  def tmpDir: Path = Paths.get(System.getProperty("java.io.tmpdir"))

  def workDir: Path = ConfigHelper.workDir1

}
