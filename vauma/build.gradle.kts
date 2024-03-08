import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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

    //ui tests kaspresso
    androidTestImplementation(libs.kaspresso)
    androidTestImplementation(libs.kaspresso.compose)

    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.test.manifest)

    //firebase crashlytics
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

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

    //decompose
    implementation (libs.decompose)
    implementation(libs.decompose.jetpack.compose)

    //mvi kotlin
    implementation(libs.mvi.kotlin)
    implementation(libs.mvi.kotlin.main)
    implementation(libs.mvi.kotlin.coroutine.extensions)
    implementation(libs.mvi.kotlin.logging)


    implementation(project(":features-signin"))
    implementation(project(":features-signup"))
    implementation(project(":features-forgotPassword"))
    implementation(project(":features-account"))
    implementation(project(":features-home"))
    implementation(project(":features-favourites"))
    implementation(project(":features-detailInfo"))
    implementation(project(":features-search"))
    implementation(project(":features-video"))

    implementation(project(":data"))
    implementation(project(":data-network"))
    implementation(project(":data-store-client"))
    implementation(project(":data-database"))

    implementation(project(":logger"))

    implementation(project(":core-theme"))
    implementation(project(":core-utils"))
    implementation(project(":core-extensions"))
    implementation(project(":core-presentation"))

    implementation(project(":core-models"))
}

ksp {
    arg("me.tatarka.inject.dumpGraph", "true")
}

tasks.withType<Test> {
    useJUnitPlatform()
}