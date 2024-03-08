@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        compileSdkPreview = "UpsideDownCake"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core-extensions"))
    implementation(project(":core-models"))
    implementation(project(":data-network"))
    implementation(project(":data-store-client"))

    //kotlin
    implementation(libs.core.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //ktor
    implementation(libs.ktor.client.android)

    //mvi kotlin
    implementation(libs.mvi.kotlin)
    implementation(libs.mvi.kotlin.main)
    implementation(libs.mvi.kotlin.coroutine.extensions)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //paging 3
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

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

    //room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
}