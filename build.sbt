lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "module-layers",
    organization := "com.github.tammo",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.18"
  )
