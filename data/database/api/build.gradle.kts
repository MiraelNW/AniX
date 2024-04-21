plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    //kotlin
    implementation(libs.coroutine.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    api(project(":core:models"))
}