plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.storeclient.impl"
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

    implementation(libs.appcompat)
    implementation(libs.com.google.android.material.material)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //data store
    implementation(libs.datastore.preferences)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //pluto
    implementation(libs.pluto.datastore)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":core:models"))
    implementation(project(":data:storeclient:api"))
}