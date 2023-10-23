import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
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

        val properties = Properties().apply {
            load(rootProject.file("local.properties").reader())
        }

        buildConfigField("String", "BASE_URL", "${properties["BASE_URl_KEY"]}")
        buildConfigField("String", "ANIME_LIST_URL", "${properties["ANIME_LIST_URl_KEY"]}")
        buildConfigField("String", "SEARCH_URL", "${properties["SEARCH_ANIME_URl_KEY"]}")
        buildConfigField("String", "NEW_CATEGORY_URL", "${properties["NEW_CATEGORY_URl_KEY"]}")
        buildConfigField(
            "String",
            "POPULAR_CATEGORY_URL",
            "${properties["POPULAR_CATEGORY_URl_KEY"]}"
        )
        buildConfigField("String", "FILMS_CATEGORY_URL", "${properties["FILMS_CATEGORY_URl_KEY"]}")
        buildConfigField("String", "NAME_CATEGORY_URL", "${properties["NAME_CATEGORY_URl_KEY"]}")
        buildConfigField("String", "AUTH_REGISTER_URL", "${properties["AUTH_REGISTER_URl_KEY"]}")
        buildConfigField("String", "AUTH_LOGIN_URL", "${properties["AUTH_LOGIN_URl_KEY"]}")
        buildConfigField(
            "String",
            "AUTH_CHANGE_PASSWORD_URL",
            "${properties["AUTH_CHANGE_PASSWORD_URl_KEY"]}"
        )
        buildConfigField("String", "AUTH_LOGOUT_URL", "${properties["AUTH_LOGOUT_URl_KEY"]}")
        buildConfigField("String", "AUTH_REFRESH_URL", "${properties["AUTH_REFRESH_URl_KEY"]}")

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
        kotlinCompilerExtensionVersion = "1.2.0"
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

    //exoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    //images
    implementation(libs.coil)
    implementation(libs.coil.video)
    implementation(libs.coil.compose)
    implementation(libs.landscapist.glide)
    implementation(libs.landscapist.transformation)
    implementation(libs.landscapist.animation)
    implementation(libs.landscapist.palette)

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

    //retrofit
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.lifecycle.viewmodel.compose)

    //dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //splash screen api
    implementation(libs.core.splashscreen)

    //accompanist system ui
    implementation(libs.accompanist.systemuicontroller)

    //accompanist pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    //ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging.jvm)

    //google oauth
    implementation(libs.play.services.auth)
    //vk auth
    implementation(libs.vk.sdk.core)
    implementation(libs.vk.sdk.api)

    //room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    //lottie animations
    implementation(libs.lottie.compose)

    //paging 3
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

    //data store
    implementation(libs.datastore.preferences)
}

hilt {
    enableAggregatingTask = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}