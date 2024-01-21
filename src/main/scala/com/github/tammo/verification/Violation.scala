package com.github.tammo.verification

import com.github.tammo.domain.ModuleLayersDomain.Layer

private[verification] case class Violation(
    module: Module,
    dependency: Module,
    allowedLayers: Iterable[Layer]
)
