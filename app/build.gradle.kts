import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraelDev.vauma"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.miraelDev.vauma"
        minSdk = 24
        targetSdk = 33
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
        freeCompilerArgs = listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + project.buildDir.absolutePath + "/compose_metrics"
        )
        freeCompilerArgs = listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + project.buildDir.absolutePath + "/compose_metrics"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.ui.util)
    implementation(libs.material)
    implementation(libs.lifecycle.runtime.compose)

    //test
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //mockito
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.test.manifest)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.gson)

    //dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //splash screen api
    implementation(libs.core.splashscreen)

    //accompanist system ui
    implementation(libs.accompanist.systemuicontroller)

    //paging 3
    implementation(libs.paging.runtime.ktx)

    //exoplayer
    implementation(libs.media3.exoplayer)

    //data store
    implementation(libs.datastore.preferences)

    implementation(project(":features:signIn"))
    implementation(project(":features:signUp"))
    implementation(project(":features:forgotPassword"))
    implementation(project(":features:account"))
    implementation(project(":features:home"))
    implementation(project(":features:favourites"))
    implementation(project(":features:detailInfo"))
    implementation(project(":features:search"))
    implementation(project(":features:videoScreen"))

    implementation(project(":data"))

    implementation(project(":core:theme"))
    implementation(project(":core:utils"))
    implementation(project(":core:extensions"))
    implementation(project(":core:presentation"))

    implementation(project(":core:models:anime"))
    implementation(project(":core:models:auth"))
    implementation(project(":core:models:result"))
    implementation(project(":core:models:user"))
    implementation(project(":core:models:video"))
}

hilt {
    enableAggregatingTask = true
}

kapt {
    correctErrorTypes = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}