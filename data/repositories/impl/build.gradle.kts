plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.test.library)
    alias(libs.plugins.vauma.kotlin.inject.library)
    alias(libs.plugins.vauma.app.flavor.library)
    alias(libs.plugins.devtools.ksp)
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.repositories.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        create("debugR8") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            multiDexEnabled = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            multiDexEnabled = true
        }
    }
}

dependencies {

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //ktor
    implementation(libs.ktor.client.cio)

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

    implementation(project(":core:extensions"))
    implementation(project(":core:models"))

    implementation(project(":data:network:api"))
    implementation(project(":data:storeclient:api"))
    implementation(project(":data:database:api"))
    implementation(project(":data:repositories:api"))
}