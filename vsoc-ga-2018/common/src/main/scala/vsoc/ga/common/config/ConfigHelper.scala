package vsoc.ga.common.config

import java.nio.file.{Path, Paths}

import com.typesafe.config.ConfigFactory

object ConfigHelper {

  private val props = ConfigFactory.load()

  def homeDir: Path = Paths.get(System.getProperty("user.home"))

  def tmpDir: Path = Paths.get(System.getProperty("java.io.tmpdir"))

  def workDir: Path = Paths.get(props.getString("vsoc.workdir"))

  def hostName: String = props.getString("hostname")

}
