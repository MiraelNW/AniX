plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.test.library)
    alias(libs.plugins.vauma.app.flavor.library)
    alias(libs.plugins.vauma.coil.library)
}

android {
    namespace = "com.miraeldev.imageloader.api"

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