lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "module-layers",
    organization := "com.github.tammo",
    version := "0.1.0-SNAPSHOT",
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
    credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      System.getenv("SONATYPE_USERNAME"),
      System.getenv("SONATYPE_PASSWORD")
    ),
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
  )
