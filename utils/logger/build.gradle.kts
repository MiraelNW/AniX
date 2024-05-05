@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.test.library)
    alias(libs.plugins.vauma.kotlin.inject.library)
    alias(libs.plugins.vauma.app.flavor.library)
    alias(libs.plugins.devtools.ksp)
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.miraeldev.logger_impl"

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
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}