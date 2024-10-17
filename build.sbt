name := """Kniffel"""
organization := "htwg"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers ++= Resolver.sonatypeOssRepos("snapshots")

scalaVersion := "2.13.14"

scalacOptions ++= Seq("-Ytasty-reader")

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
)