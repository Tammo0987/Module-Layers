package com.github.tammo.verification

import com.github.tammo.ModuleLayersPlugin.autoImport.{architectureStyle, layer}
import sbt.Keys._
import sbt._

object VerificationTask {

  def verifyArchitectureStyleTask: Def.Initialize[Task[Unit]] = Def.task {
    val logger = streams.value.log
    val configuredArchitectureStyle = architectureStyle.value

    val currentProject = thisProject.value
    val projectLayer = layer.value

    val currentSettingsData = settingsData.value

    logger.debug(
      s"Verify structure of module ${currentProject.id} against ${configuredArchitectureStyle.styleName}."
    )

    val dependencies = currentProject.dependencies.map { dependency =>
      val layerOfDependency = (dependency.project / layer)
        .get(currentSettingsData)
        .get

      Module(dependency.project.project, layerOfDependency, Set.empty)
    }.toSet

    val module = Module(currentProject.id, projectLayer, dependencies)
    val violations =
      ProjectStructureVerifier.verify(module, configuredArchitectureStyle)

    violations.foreach { violation =>
      val Violation(_, dependency, allowedLayers) = violation
      logger.error(
        s"Violation detected for module ${module.name}." +
          s" It has ${dependency.name} as dependency which has ${dependency.layer} as layer." +
          s" Allowed layers: ${allowedLayers.mkString(", ")}."
      )
    }

    if (violations.nonEmpty) {
      throw new IllegalStateException(
        s"Module ${module.name} contains violations against architecture style: ${configuredArchitectureStyle.styleName}."
      )
    }
  }

}
