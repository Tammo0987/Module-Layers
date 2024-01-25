package com.github.tammo.verification

import com.github.tammo.ModuleLayersPlugin.{Aggregate, NoArchitectureStyle}
import com.github.tammo.domain.ModuleLayersDomain.*
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class ProjectStructureVerifierTest extends AnyFlatSpecLike with Matchers {

  private val emptyModule = Module("empty", DependencyLayer, Set.empty)

  private val moduleWithDependency =
    Module("with-dependency", Aggregate, Set(emptyModule))

  private case object DependencyLayer extends Layer

  private def architectureStyleForTest(
      testPermits: Permit*
  ): ArchitectureStyle = new ArchitectureStyle {
    override def styleName: String = "test-style"

    override def permits: Iterable[Permit] = testPermits
  }

  it should "have no violation if module has no dependencies" in {
    val violations =
      ProjectStructureVerifier.verify(emptyModule, NoArchitectureStyle)

    violations shouldBe Set.empty
  }

  behavior of "explicit permit"

  it should "have no violations if module with dependency has explicit permit" in {
    val architectureStyle: ArchitectureStyle =
      architectureStyleForTest(Aggregate --> DependencyLayer)

    val violations =
      ProjectStructureVerifier.verify(
        moduleWithDependency,
        architectureStyle
      )

    violations shouldBe Set.empty
  }

  it should "have violation if module with dependency has explicit permit for other layer" in {
    case object OtherLayer extends Layer

    val architectureStyle: ArchitectureStyle =
      architectureStyleForTest(Aggregate --> OtherLayer)

    val violations =
      ProjectStructureVerifier.verify(
        moduleWithDependency,
        architectureStyle
      )

    violations shouldBe Set(
      Violation(moduleWithDependency, emptyModule, Set(OtherLayer))
    )
  }

  behavior of "permit all"

  it should "have no violations if module with dependency has permit all" in {
    val architectureStyle = architectureStyleForTest(PermitAll(Aggregate))

    val violations =
      ProjectStructureVerifier.verify(moduleWithDependency, architectureStyle)

    violations shouldBe Set.empty
  }

}
