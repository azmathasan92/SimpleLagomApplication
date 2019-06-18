
name := "simple-crud-in-lagom-using-embedded-cassandra"

version := "0.1"

scalaVersion := "2.12.8"


val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

// https://mvnrepository.com/artifact/org.julienrf/play-json-derived-codecs

lazy val `product` = (project in file("."))
  .aggregate(`product-api`, `product-impl`)

lazy val `product-api` = (project in file("product-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
//  .settings(dockerBaseImage := "openjdk:8-jdk-alpine")

lazy val `product-impl` = (project in file("product-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      "org.julienrf" %% "play-json-derived-codecs" % "5.0.0",
      macwire,
      scalaTest,

    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`product-api`)

coverageMinimum in ThisBuild := 50
coverageFailOnMinimum in ThisBuild := true
scalastyleFailOnError in ThisBuild := true
scalastyleFailOnWarning in ThisBuild := true

lagomKafkaEnabled in ThisBuild := false
lagomCassandraEnabled in ThisBuild := false
lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")


