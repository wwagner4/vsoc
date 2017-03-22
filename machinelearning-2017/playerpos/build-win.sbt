val breezeVersion = "0.13"

lazy val root = (project in file("."))
  .settings(
    name := "playerpos",
    scalaVersion := "2.12.1",
    resolvers += "Local Maven Repository" at "file:///C:/ta30/nutzb/_m2_repo/",
    libraryDependencies += "net.entelijan" % "vsoc-core" % "0.0.1-SNAPSHOT",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  	libraryDependencies += "org.scalanlp" %% "breeze" % breezeVersion,
  	libraryDependencies += "org.scalanlp" %% "breeze-natives" % breezeVersion
  )
  
// scalacOptions += "-deprecation",
