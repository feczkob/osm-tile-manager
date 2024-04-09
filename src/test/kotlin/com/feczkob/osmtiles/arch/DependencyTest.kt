package com.feczkob.osmtiles.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition

private const val JAVA_PACKAGE = "java.."
private const val KOTLIN_PACKAGE = "kotlin.."
private const val KOTLINX_PACKAGE = "kotlinx.."
private const val MODEL_PACKAGE = "..model.."
private const val FETCHABLE_PACKAGE = "..fetchable.."

@AnalyzeClasses(packages = ["com.feczkob.osmtiles"])
class DependencyTest {
    @ArchTest
    fun modelShouldOnlyAccessModel(classes: JavaClasses) =
        ArchRuleDefinition.classes().that().resideInAPackage(MODEL_PACKAGE)
            .should().onlyAccessClassesThat()
            .resideInAnyPackage(MODEL_PACKAGE, JAVA_PACKAGE, KOTLIN_PACKAGE)
            .check(classes)

    @ArchTest
    fun fetchableShouldOnlyAccessModelAndFetchable(classes: JavaClasses) =
        ArchRuleDefinition.classes().that().resideInAPackage(FETCHABLE_PACKAGE)
            .should().onlyAccessClassesThat()
            .resideInAnyPackage(FETCHABLE_PACKAGE, MODEL_PACKAGE, JAVA_PACKAGE, KOTLIN_PACKAGE, KOTLINX_PACKAGE)
            .check(classes)
}
