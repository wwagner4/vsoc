import java.util.Properties

lazy val root = (project in file("."))
  .settings(
    name := "create-data",
    scalaVersion := "2.12.1",
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.24"
  )
  
// scalacOptions += "-deprecation",
