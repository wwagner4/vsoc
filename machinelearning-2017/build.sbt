lazy val breezeVersion = "0.13"
lazy val _scalaVersion = "2.11.11"
lazy val dl4jVersion = "0.8.0"
lazy val userHome = System.getProperty("user.home")

//noinspection SpellCheckingInspection
lazy val commonSettings = Seq(
  organization := "net.entelijan.vsoc",
  scalaVersion := _scalaVersion,
  version := "0.0.1-SNAPSHOT",
  resolvers += "Local Maven Repository" at s"file://$userHome/.m2/repository",
  //resolvers += "Local Maven Repository" at "file:///C:/ta30/nutzb/_m2_repo/",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.2" % "test")

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "machinelearning-root")
  .aggregate(common, create_data, dl4j_datavec, dl4j_training)

lazy val common = (project in file("common"))
  .settings(
    commonSettings,
    name := "machinelearning-common")

lazy val create_data = (project in file("create-data"))
  .settings(
    commonSettings,
    name := "create-data",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25")
  .dependsOn(common)

lazy val dl4j_datavec = (project in file("dl4j-datavec"))
  .settings(
    commonSettings,
    name := "dl4j-datavec",
    libraryDependencies += "org.datavec" %% "datavec-spark" % (dl4jVersion + "_spark_2"))
  .dependsOn(common)

lazy val dl4j_training = (project in file("dl4j-training"))
  .settings(
    commonSettings,
    name := "dl4j-training",
    libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % dl4jVersion,
    libraryDependencies += "org.nd4j" % "nd4j-native-platform" % dl4jVersion,
    libraryDependencies += "org.planet42" %% "laika-core" % "0.7.0")
  .dependsOn(create_data)

// scalacOptions += "-deprecation",
