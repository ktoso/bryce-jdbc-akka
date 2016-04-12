name := "bryce-jdbc-akka"

organization := "bryce-jdbc-akka"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

val AkkaVersion = "2.4.0"

resolvers += Resolver.typesafeRepo("releases")

resolvers += "jcenter" at "https://bintray.com/bintray/jcenter"


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion

libraryDependencies += "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.2.16"

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
