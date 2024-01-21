package com.github.tammo.verification

import com.github.tammo.domain.ModuleLayersDomain._

object ProjectStructureVerifier {

  def verify(
      module: Module,
      architectureStyle: ArchitectureStyle
  ): Iterable[Violation] = {
    val potentiallyAllowedLayers =
      architectureStyle.permits.foldLeft[Option[Set[Layer]]](Some(Set.empty)) {
        case (None, _) => None
        case (result, PermitAll(layer)) =>
          if (module.layer == layer) {
            None
          } else {
            result
          }
        case (result @ Some(allowedLayers), permit: ExplicitPermit) =>
          if (module.layer == permit.layer) {
            Some(allowedLayers ++ permit.allowedDependencyLayers)
          } else {
            result
          }
      }

    potentiallyAllowedLayers match {
      case None =>
        // None expresses the absence of forbidden layers
        Iterable.empty
      case Some(allowedLayers) =>
        module.dependencies.flatMap(
          isDependencyViolated(module, _, allowedLayers)
        )
    }
  }

  private def isDependencyViolated(
      module: Module,
      dependency: Module,
      allowedLayers: Set[Layer]
  ): Option[Violation] = {
    if (allowedLayers.contains(dependency.layer)) {
      None
    } else {
      Some(Violation(module, dependency, allowedLayers))
    }
  }

}
