ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "AlertExtraction"
  )

libraryDependencies ++= List(
  "com.typesafe" % "config" % "1.4.2",
  "com.softwaremill.sttp.client3" %% "core" % "3.8.15",
  "com.softwaremill.sttp.client3" %% "circe" % "3.8.15",
  "io.circe" %% "circe-generic" % "0.14.5",
  "io.circe" %% "circe-parser" % "0.14.5",
  "com.github.pathikrit" %% "better-files" % "3.9.2"
)
