lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "module-layers",
    organization := "com.github.tammo",
    scalaVersion := "2.12.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test,
    developers += Developer(
      "tammo-steffens",
      "Tammo Steffens",
      "",
      url("https://github.com/Tammo0987")
    ),
    licenses := List(
      "MIT License" -> url(
        "https://raw.githubusercontent.com/Tammo0987/Module-Layers/main/LICENSE"
      )
    ),
    sonatypeRepository := "https://central.sonatype.com/service/local"
  )

ThisBuild / sonatypeCredentialHost := "https://central.sonatype.com"
