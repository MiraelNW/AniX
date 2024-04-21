plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("app.cash.sqldelight") version "2.0.0"
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.database.impl"
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

    implementation(libs.core.ktx)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //immutable list
    implementation(libs.kotlinx.collections.immutable)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //sqlDelight
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutine.exstension)

    implementation(project(":core:models"))
    implementation(project(":core:extensions"))
    implementation(project(":data:database:api"))
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.miraeldev")
        }
    }
}