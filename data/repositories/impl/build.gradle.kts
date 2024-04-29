plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.repositories.impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    //kotlin
    implementation(libs.core.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //ktor
    implementation(libs.ktor.client.cio)

    //mvi kotlin
    implementation(libs.mvi.kotlin)
    implementation(libs.mvi.kotlin.main)
    implementation(libs.mvi.kotlin.coroutine.extensions)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //immutable list
    implementation(libs.kotlinx.collections.immutable)

    //data store
    implementation(libs.datastore.preferences)

    //vk auth
    implementation(libs.vk.sdk.core)
    implementation(libs.vk.sdk.api)

    //exoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    //junit
    testImplementation(libs.junit)

    //mockk
    testImplementation(libs.mockk)

    //coroutine test
    testImplementation (libs.kotlinx.coroutines.test)

    //flow test
    testImplementation(libs.turbine)

    implementation(project(":core:extensions"))
    implementation(project(":core:models"))

    implementation(project(":data:network:api"))
    implementation(project(":data:storeclient:api"))
    implementation(project(":data:database:api"))
    implementation(project(":data:repositories:api"))
}