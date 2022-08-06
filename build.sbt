name := "CFPRandomizer"

version := "0.21"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "com.1stleg" % "jnativehook" % "2.1.0",
  "com.novocode" % "junit-interface" % "0.11" % Test
)

mainClass in assembly := Some("x74R45.cfpro_randomizer.Main")
