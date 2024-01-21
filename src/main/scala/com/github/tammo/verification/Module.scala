package com.github.tammo.verification

import com.github.tammo.domain.ModuleLayersDomain.Layer

private[verification] case class Module(
    name: String,
    layer: Layer,
    dependencies: Set[Module]
)
