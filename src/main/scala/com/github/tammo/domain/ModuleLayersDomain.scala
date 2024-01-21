package com.github.tammo.domain

object ModuleLayersDomain {

  trait Layer {

    def -->(dependentLayer: Layer): ExplicitPermit =
      ExplicitPermit(this, Iterable(dependentLayer))

    def -->(dependentLayers: Iterable[Layer]): ExplicitPermit =
      ExplicitPermit(this, dependentLayers)

    def &(other: Layer): Iterable[Layer] = Iterable(this, other)

  }

  sealed trait Permit

  case class ExplicitPermit(
      layer: Layer,
      allowedDependencyLayers: Iterable[Layer]
  ) extends Permit

  case class PermitAll(layer: Layer) extends Permit

  trait ArchitectureStyle {

    def styleName: String

    def permits: Iterable[Permit] = Iterable.empty

    def ++(other: ArchitectureStyle): ArchitectureStyle =
      new ArchitectureStyle {

        override def styleName: String = other.styleName

        override def permits: Iterable[Permit] =
          ArchitectureStyle.this.permits ++ other.permits
      }

  }

}
