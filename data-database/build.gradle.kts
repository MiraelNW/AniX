import app.cash.sqlite.migrations.Database

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.cash.sqldelight") version "2.0.0"
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.data_database"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core-models"))
    implementation(project(":core-extensions"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.com.google.android.material.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //immutable list
    implementation(libs.kotlinx.collections.immutable)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //sqlDelight
    implementation("app.cash.sqldelight:android-driver:2.0.0")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
    implementation("app.cash.sqldelight:androidx-paging3-extensions:2.0.0")

    //room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.miraeldev")
        }
    }
}