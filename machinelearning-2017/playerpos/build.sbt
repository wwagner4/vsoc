lazy val root = (project in file("."))
  .settings(
    name := "playerpos",
    scalaVersion := "2.12.1",
    resolvers += "Local Maven Repository" at "file://"+  Path.userHome.absolutePath + "/.m2/repository",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT"
  )