plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.compose.library)
    alias(libs.plugins.vauma.coil.library)
    alias(libs.plugins.vauma.app.flavor.library)
}

android {
    namespace = "com.miraeldev.designsystem"

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
    //lottie animations
    implementation(libs.lottie.compose)

    implementation(project(":core:extensions"))
    implementation(project(":core:theme"))

    implementation(project(":utils:imageloader:api"))
}