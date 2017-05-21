lazy val root = (project in file("."))
  .settings(
    name := "create-data",
    organization := "net.entelijan.vsoc",
    scalaVersion := "2.12.1",
    resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.24"
  )
  
// scalacOptions += "-deprecation",
