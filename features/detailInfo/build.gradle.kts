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
}

android {
    namespace = "com.miraeldev.detailinfo"

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
    implementation(project(":core:theme"))
    implementation(project(":core:extensions"))
    implementation(project(":core:designsystem"))

    implementation(project(":utils:imageloader:api"))

    implementation(project(":data:storeclient:api"))
    implementation(project(":data:network:api"))
}