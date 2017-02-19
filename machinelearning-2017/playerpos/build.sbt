lazy val root = (project in file("."))
  .settings(
    name := "playerpos",
    scalaVersion := "2.11.8",
    resolvers += "Local Maven Repository" at "file://"+  Path.userHome.absolutePath + "/.m2/repository",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.0.0"
  )