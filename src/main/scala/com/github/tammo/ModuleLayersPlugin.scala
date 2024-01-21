package com.github.tammo

import com.github.tammo.domain.ModuleLayersDomain._
import com.github.tammo.verification.VerificationTask
import sbt.Keys.compile
import sbt.{AutoPlugin, Compile, Def}

object ModuleLayersPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport extends ModuleLayersKeys

  import autoImport._

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    layer := Aggregate,
    verifyStructure := VerificationTask.verifyArchitectureStyleTask.value,
    compile := ((Compile / compile) dependsOn verifyStructure).value
  )

  case object Aggregate extends Layer

  case object NoArchitectureStyle extends ArchitectureStyle {
    override def permits: Iterable[Permit] = Iterable(PermitAll(Aggregate))

    override def styleName: String = "No Style"
  }

}
