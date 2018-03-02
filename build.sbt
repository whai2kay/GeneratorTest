name := "GeneratorTest"

version := "0.1"

scalaVersion := "2.12.4"

resolvers ++= Seq("Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "SonaType" at "https://oss.sonatype.org/content/groups/public",
  "Typesafe maven releases" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/")


//stable snapshot of scorex
val scorexVersion = "04b0b5be-SNAPSHOT"


libraryDependencies += "org.scorexfoundation" %% "scorex-core" % scorexVersion
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"