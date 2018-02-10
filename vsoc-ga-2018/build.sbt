lazy val userHome = System.getProperty("user.home")

lazy val commonSettings = Seq(
  organization := "net.entelijan",
  scalaVersion := "2.12.4",
  version := "0.0.1-SNAPSHOT",
  resolvers += "Local Maven Repository" at s"file://$userHome/.m2/repository",
  //resolvers += "Local Maven Repository" at "file:///C:/ta30/nutzb/_m2_repo/",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.2" % "test",
)

lazy val root = (project in file("."))
  .settings(
    name := "vsoc-ga-2018-root",
  )
  .aggregate(matches, genetic, trainga)


lazy val matches = (project in file("matches"))
  .settings(
    name := "matches",
    commonSettings,
    // Must be installed in your local repository using maven.
    // Is a module of https://github.com/wwagner4/vsoc.git
    // Module names :
    // vsoc/vsoc-2007/vsoc-core
    // vsoc/vsoc-2007/atan-2007 (transitive dependency)
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
  )

lazy val genetic = (project in file("genetic"))
  .settings(
    name := "genetic",
    commonSettings,
  )

lazy val trainga = (project in file("trainga"))
  .settings(
    name := "trainga",
    commonSettings,
    libraryDependencies += "org.deeplearning4j" % "deeplearning4j-nn" % "0.9.1",
    libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1",
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.4",
  ).dependsOn(matches, genetic)


