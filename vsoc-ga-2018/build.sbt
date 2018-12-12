

lazy val userHome = System.getProperty("user.home")

lazy val commonSettings = Seq(
  organization := "net.entelijan",
  scalaVersion := "2.12.7",
  version := "0.0.1-SNAPSHOT",
  fork := true,
  // libraries like vsoc-core and viz
  resolvers += "mystuff" at "https://raw.githubusercontent.com/wwagner4/final/master/",
  // If your maven repository is located at another file location define this in $HOME/.sbt/<version>/local.sbt
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.2" % "test",
  libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.10",
  libraryDependencies += "net.entelijan" %% "viz" % "0.1",
)

lazy val root = (project in file("."))
  .settings(name := "vsoc-ga-2018-root")
  .aggregate(common, matches, genetic, trainga)

lazy val matches = (project in file("matches"))
  .settings(
    name := "matches",
    commonSettings,
    // Must be installed in your local repository using maven.
    // Is a module of https://github.com/wwagner4/vsoc.git
    // cd vsoc/vsoc-2007 && mvn install
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1",
  )

lazy val common = (project in file("common"))
  .settings(
    name := "common",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.7",
    libraryDependencies += "org.apache.commons" % "commons-compress" % "1.18",
    libraryDependencies += "org.tukaani" % "xz" % "1.8",
    commonSettings,
  )

lazy val genetic = (project in file("genetic"))
  .settings(name := "genetic", commonSettings)
  .dependsOn(common)

lazy val trainga = (project in file("trainga"))
  .settings(
    name := "trainga",
    commonSettings,
    libraryDependencies += "org.deeplearning4j" % "deeplearning4j-nn" % "0.9.1" exclude("com.github.stephenc.findbugs", "findbugs-annotations"),
    libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1" exclude("com.github.stephenc.findbugs", "findbugs-annotations"))
  .dependsOn(matches, genetic)
