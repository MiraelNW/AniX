plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //immutable list
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)
}