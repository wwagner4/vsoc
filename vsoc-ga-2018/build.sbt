lazy val userHome = System.getProperty("user.home")

lazy val commonSettings = Seq(
  organization := "net.entelijan",
  scalaVersion := "2.12.4",
  version := "0.0.1-SNAPSHOT",
  //resolvers += "Local Maven Repository" at s"file://$userHome/.m2/repository",
  resolvers += "Local Maven Repository" at "file:///C:/ta30/nutzb/_m2_repo/",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.2" % "test",
)

lazy val root = (project in file("."))
  .settings(
    name := "vsoc-ga-2018-root",
  )
  .aggregate(matches)


lazy val matches = (project in file("matches"))
  .settings(
    name := "matches",
    commonSettings,
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
  )
