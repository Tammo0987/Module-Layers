package com.github.tammo

import com.github.tammo.domain.ModuleLayersDomain.{ArchitectureStyle, Layer}
import sbt._

trait ModuleLayersKeys {

  lazy val architectureStyle: SettingKey[ArchitectureStyle] = settingKey(
    "Defines architecture style, which should be verified."
  )

  lazy val layer: SettingKey[Layer] = settingKey("Defines layer for a project.")

  lazy val verifyStructure: TaskKey[Unit] = taskKey(
    "Verifies the current modules and their dependencies, against the configured layers and architecture style."
  )

}
