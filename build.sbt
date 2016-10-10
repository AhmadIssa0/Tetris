enablePlugins(ScalaJSPlugin)

name := "Scala.js Tetris"

scalaVersion := "2.11.7" // or any other Scala version >= 2.10.2

scalaJSUseRhino in Global := false // don't use Rhino, use Node.js

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0"
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

skip in packageJSDependencies := false
jsDependencies +=
  "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"

mainClass in Compile := Some("tetris.Tetris")

persistLauncher in Compile := true
