package vsoc.ga.common.config

import java.nio.file.{Path, Paths}
import java.util.Properties

object ConfigHelper {

  private val props = {
    val _props = new Properties()
    val url = getClass.getClassLoader.getResource("application.properties")
    if (url == null) throw new IllegalMonitorStateException("Could not find 'application.properties' in classpath. Check 'common/src/main/resources")
    _props.load(url.openStream())
    _props
  }

  def homeDir: Path = Paths.get(System.getProperty("user.home"))

  def tmpDir: Path = Paths.get(System.getProperty("java.io.tmpdir"))

  def workDir: Path = Paths.get(props.getProperty("vsoc.workdir"))

  def hostName: String = props.getProperty("vsoc.hostname")

}
