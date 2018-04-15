package vsoc.ga.common.config

import java.nio.file.{Path, Paths}

object ConfigHelper {

  def homeDir: Path = Paths.get(System.getProperty("user.home"))

  def tmpDir: Path = Paths.get(System.getProperty("java.io.tmpdir"))

  def defaultWorkDirRel: Path = Paths.get("work", "work-vsoc-ga-2018")

  def defaultWorkDir: Path = homeDir.resolve(defaultWorkDirRel)

  def defaultWorkDirWinows: Path = homeDir.resolve(defaultWorkDirRel)

}
