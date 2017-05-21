import java.util.Properties

val appProperties = settingKey[Properties]("The application properties")

appProperties := {
  val prop = new Properties()
  IO.load(prop, new File("application.properties"))
  prop
}


val breezeVersion = "0.13"

lazy val root = (project in file("."))
  .settings(
    name := "playerpos-breeze",
    scalaVersion := "2.12.1",
    resolvers += "Local Maven Repository" at appProperties.value.getProperty("m2repo"),
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  	libraryDependencies += "org.scalanlp" %% "breeze" % breezeVersion,
  	libraryDependencies += "org.scalanlp" %% "breeze-natives" % breezeVersion,
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.24"
  )
  
// scalacOptions += "-deprecation",
