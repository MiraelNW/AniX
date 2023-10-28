import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        compileSdkPreview = "UpsideDownCake"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core:extensions"))
    implementation(project(":core:models:anime"))
    implementation(project(":core:models:result"))
    implementation(project(":core:models:auth"))
    implementation(project(":core:models:user"))

    //kotlin
    implementation(libs.core.ktx)

    //dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging.jvm)

    //paging 3
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

    //immutable list
    implementation(libs.kotlinx.collections.immutable)

    //data store
    implementation(libs.datastore.preferences)

    //vk auth
    implementation(libs.vk.sdk.core)
    implementation(libs.vk.sdk.api)

    //exoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    //room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
}