lazy val breezeVersion = "0.13"
lazy val _scalaVersion = "2.12.2"

lazy val commonSettings = Seq(
  organization := "net.entelijan.vsoc",
  scalaVersion := _scalaVersion,
  version := "0.0.1-SNAPSHOT",
  resolvers += "Local Maven Repository" at "file://" + sbt.Path.userHome.absolutePath + "/.m2/repository",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.2" % "test")

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "machinelearning-root")
  .aggregate(common, create_data, playerpos_breeze)

lazy val common = (project in file("common"))
  .settings(
    commonSettings,
    name := "machinelearning-common")

lazy val create_data = (project in file("create-data"))
  .settings(
    commonSettings,
    name := "create-data",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.24")
  .dependsOn(common)


lazy val playerpos_breeze = (project in file("playerpos-breeze"))
  .settings(
    commonSettings,
    name := "playerpos-breeze",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
  	libraryDependencies += "org.scalanlp" %% "breeze" % breezeVersion,
  	libraryDependencies += "org.scalanlp" %% "breeze-natives" % breezeVersion,
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.24")
  .dependsOn(common)
  
// scalacOptions += "-deprecation",
