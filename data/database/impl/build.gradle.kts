plugins {
    alias(libs.plugins.vauma.android.library)
    alias(libs.plugins.vauma.kotlin.inject.library)
    alias(libs.plugins.sql.delight)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.vauma.app.flavor.library)
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.database.impl"

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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            multiDexEnabled = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            multiDexEnabled = true
        }
    }
}

dependencies {

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //immutable list
    implementation(libs.kotlinx.collections.immutable)

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