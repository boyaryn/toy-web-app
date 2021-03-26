val ScalatraVersion = "2.7.1"

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "com.seekingalpha"

lazy val hello = (project in file("."))
  .settings(
    name := "Toy Web App",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "9.4.35.v20201120" % "container",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
    ),
// uncomment for debugging
//    javaOptions ++= Seq(
//      "-Xdebug",
//      "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
//    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)
