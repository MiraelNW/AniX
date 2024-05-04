plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    //kotlin
    implementation(libs.coroutine.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:models"))
}