import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:8.3.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.7.0")
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("com.android.library") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.savvasdalkitsis.module-dependency-graph") version "0.10"
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

apply {
    plugin("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins (libs.detekt)
}

val autoFix = project.hasProperty("detektAutoFix")

val projectSource = file(projectDir)
val configFile = files("$rootDir/tools/detekt/detekt.yml")
//val baselineFile = file("$rootDir/tools/detekt/baseline.xml")
val kotlinFiles = "**/*.kt"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

tasks.register<Detekt>("detektRun") {
    description = "DETEKT for all modules"
    parallel = true
    ignoreFailures = false
    autoCorrect = true
    buildUponDefaultConfig = true
    setSource(projectSource)
//    baseline.set(baselineFile)
    config.setFrom(configFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

tasks.register<DetektCreateBaselineTask>("detektGenerateBaseline") {
    description = "DETEKT to build baseline for all modules"
    parallel = true
    ignoreFailures = false
    buildUponDefaultConfig = true
    setSource(projectSource)
//    baseline.set(baselineFile)
    config.setFrom(configFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
}

//apply(from = "gradle/jacoco.gradle")
apply(from = "gradle/tasks/githooks.gradle")
apply(from = "gradle/tasks/moduleDependencyGraph.gradle")


