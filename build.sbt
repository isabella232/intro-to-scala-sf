name := "intro-to-scala"

version := "0.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % Test,
  "org.http4s" %% "http4s-dsl" % "0.21.20",
  "org.http4s" %% "http4s-blaze-server" % "0.21.20",
)

scalaVersion := "2.13.3"

scalacOptions ++= Seq(
  "-Werror",
  "-deprecation"
)

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-C", "delight.LittleRed")

Compile / run / fork := true
