
enablePlugins(ScalaJSPlugin)

name := "Bloom Experiment"

scalaVersion := "2.11.7"

scalaJSUseRhino in Global := false // use Node.js

skip in packageJSDependencies := false // bundle all js dependencies

persistLauncher in Compile := true

persistLauncher in Test := false

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.4.6"
libraryDependencies += "org.singlespaced" %%% "scalajs-d3" % "0.3.1"

libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
jsDependencies += "org.webjars" % "cryptojs" % "3.1.2" / "3.1.2/rollups/md5.js"
jsDependencies += RuntimeDOM
