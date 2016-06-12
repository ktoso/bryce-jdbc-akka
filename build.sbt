name := "bryce-jdbc-akka"

organization := "bryce-jdbc-akka"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += Resolver.typesafeRepo("releases")

resolvers += "jcenter" at "https://bintray.com/bintray/jcenter"

libraryDependencies ++= {
  val akkaVersion = "2.4.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
    "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.3.1",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "org.postgresql" % "postgresql" % "9.4.1208"
  )
}

// enable sbt-revolver
Revolver.settings ++ Seq(
  Revolver.enableDebugging(port = 5050, suspend = false),
  mainClass in reStart := Some("akka.support.Application")
)