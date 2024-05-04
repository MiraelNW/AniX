plugins {
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.vauma.android.application)
}

android {
    namespace = "com.miraelDev.vauma"

    defaultConfig {
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        compileSdkPreview = "UpsideDownCake"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        create("debugR8") {
            isMinifyEnabled = true
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
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
    implementation(project(":features:signin"))
    implementation(project(":features:signup"))
    implementation(project(":features:forgotpassword"))
    implementation(project(":features:account"))
    implementation(project(":features:home"))
    implementation(project(":features:favourites"))
    implementation(project(":features:detailInfo"))
    implementation(project(":features:search"))
    implementation(project(":features:video"))

    implementation(project(":data:repositories:api"))
    implementation(project(":data:repositories:impl"))
    implementation(project(":data:network:api"))
    implementation(project(":data:network:impl"))
    implementation(project(":data:storeclient:api"))
    implementation(project(":data:storeclient:impl"))
    implementation(project(":data:database:api"))
    implementation(project(":data:database:impl"))

    implementation(project(":core:theme"))
    implementation(project(":core:utils"))
    implementation(project(":core:extensions"))
    implementation(project(":core:models"))

    implementation(project(":utils:logger"))
    implementation(project(":utils:imageloader:api"))
    implementation(project(":utils:imageloader:impl"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}