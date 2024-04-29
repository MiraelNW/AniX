import java.util.Properties

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("kotlinx-serialization")
}

android {
    namespace = "com.miraeldev.network.impl"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

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
        buildConfigField("String", "GET_BY_ID", "${properties["GET_ANIME_BY_ID"]}")
        buildConfigField("String", "SET_ANIME_FAV_STATUS", "${properties["SET_ANIME_FAVOURITE_STATUS"]}")
        buildConfigField(
            "String",
            "POPULAR_CATEGORY_URL",
            "${properties["POPULAR_CATEGORY_URl_KEY"]}"
        )
        buildConfigField("String", "FILMS_CATEGORY_URL", "${properties["FILMS_CATEGORY_URl_KEY"]}")
        buildConfigField("String", "NAME_CATEGORY_URL", "${properties["NAME_CATEGORY_URl_KEY"]}")


        buildConfigField("String", "CHECK_EMAIL", "${properties["AUTH_CHECK_EMAIL_KEY"]}")
        buildConfigField("String", "GOOGLE_LOGIN", "${properties["AUTH_GOOGLE_LOGIN_URl_KEY"]}")
        buildConfigField("String", "VK_LOGIN", "${properties["AUTH_VK_LOGIN_URl_KEY"]}")
        buildConfigField(
            "String",
            "CREATE_NEW_PASSWORD",
            "${properties["AUTH_CREATE_NEW_PASSWORD_KEY"]}"
        )
        buildConfigField(
            "String",
            "VERIFY_OTP_FORGOT_PASSWORD",
            "${properties["AUTH_VERIFY_OTP_FORGOT_PASSWORD_URl_KEY"]}"
        )
        buildConfigField("String", "AUTH_REGISTER_URL", "${properties["AUTH_REGISTER_URl_KEY"]}")
        buildConfigField(
            "String",
            "AUTH_VERIFY_OTP_URL",
            "${properties["AUTH_VERIFY_OTP_URl_KEY"]}"
        )
        buildConfigField("String", "AUTH_LOGIN_URL", "${properties["AUTH_LOGIN_URl_KEY"]}")
        buildConfigField(
            "String",
            "AUTH_CHANGE_PASSWORD_URL",
            "${properties["AUTH_CHANGE_PASSWORD_URl_KEY"]}"
        )
        buildConfigField("String", "AUTH_LOGOUT_URL", "${properties["AUTH_LOGOUT_URl_KEY"]}")
        buildConfigField("String", "AUTH_REFRESH_URL", "${properties["AUTH_REFRESH_URl_KEY"]}")

        buildConfigField("String", "GET_USER_INFO", "${properties["GET_USER_INFO"]}")
        buildConfigField("String", "CHANGE_PASSWORD", "${properties["CHANGE_PASSWORD"]}")
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

    //kotlin-inject
    ksp(libs.kotlin.inject.ksp)
    implementation(libs.kotlin.inject.runtime)

    //ktor
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging.jvm)

    //pluto
    implementation(libs.pluto.ktor.intercepter)

    implementation(project(":core:extensions"))
    implementation(project(":core:models"))

    implementation(project(":data:storeclient:api"))
    implementation(project(":data:network:api"))
    implementation(project(":data:database:api"))
}