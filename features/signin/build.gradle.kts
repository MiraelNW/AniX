@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.compose.library)
    alias(libs.plugins.vauma.test.library)
    alias(libs.plugins.vauma.mvi.decompose.library)
    alias(libs.plugins.vauma.coil.library)
    alias(libs.plugins.vauma.kotlin.inject.library)
    alias(libs.plugins.vauma.app.flavor.library)
    alias(libs.plugins.devtools.ksp)
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.signin"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
        create("debugR8") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            multiDexEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            multiDexEnabled = true
        }
    }
}

dependencies {
    //google oauth
    implementation(libs.play.services.auth)

    //vk auth
    implementation(libs.vk.sdk.core)
    implementation(libs.vk.sdk.api)

    implementation(project(":core:extensions"))
    implementation(project(":core:designsystem"))
}