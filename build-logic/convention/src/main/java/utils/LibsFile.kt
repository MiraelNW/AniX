package utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        print(libs.versionAliases)
        return extensions.getByType<VersionCatalogsExtension>().named("libs")
    }
//    get(): VersionCatalog = {  }